package com.avasthi.varahamihir.common.security.filters;


import com.avasthi.varahamihir.common.utils.MaskSensitiveData;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensitiveDataFilter extends PatternLayout {


    private static final String MASKCARD = "[FILTERED]";


    @Override
    public String format(LoggingEvent event) {

        if (event.getMessage() instanceof String) {
            String message = event.getRenderedMessage();
            for (MaskSensitiveData sensitiveData : MaskSensitiveData.values()) {

                Pattern PATTERNCARD =
                        Pattern.compile(sensitiveData.getRegEx(), Pattern.CASE_INSENSITIVE);
                Matcher matcher = PATTERNCARD.matcher(message);

                if (matcher.find()) {
                    String maskedMessage = matcher.group(2);
                    message = message.replaceFirst(maskedMessage, MASKCARD);


                }
            }
            Throwable throwable =
                    event.getThrowableInformation() != null ?
                            event.getThrowableInformation().getThrowable() : null;

            LoggingEvent maskedEvent = new LoggingEvent(
                    event.fqnOfCategoryClass,
                    Logger.getLogger(event.getLoggerName()),
                    event.timeStamp,
                    event.getLevel(),
                    message,
                    throwable);

            return super.format(maskedEvent);
        }

        return super.format(event);

    }
}
