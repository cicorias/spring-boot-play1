package com.cicoria.samples.clidemo.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageFilter {
    private final Pattern pattern;
    private final HashMap<String,Object> replacements;
    private final SimpleDateFormat sdf;

    @Bean
    public static MessageFilter getInstance(){
        return new MessageFilter();
    }

    public MessageFilter(){
        // NOTE: raw regex \{\{\$(.+?)\}\} - use https://regex101.com/ to convert
        String regex = "\\{\\{\\$(.+?)\\}\\}";
        pattern = Pattern.compile(regex);
        replacements = new HashMap<String,Object>();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public String replace(String text){
        Matcher matcher = pattern.matcher(text);
        Date date = new Date(System.currentTimeMillis());
        long ut1 = date.getTime() / 1000L;
        //long ut1 = Instant.now().getEpochSecond();
        // TODO: externalize replacements
        replacements.put("timestamp", ut1);
        replacements.put("isoTimestamp", sdf.format(date));
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            Object replacement = replacements.get(matcher.group(1));
            builder.append(text.substring(i, matcher.start()));
            if (replacement == null)
                builder.append(matcher.group(0));
            else
                builder.append(replacement);
            i = matcher.end();
        }
        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }
}
