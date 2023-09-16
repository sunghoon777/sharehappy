package weShare.sharehappy.service;

import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import java.util.Locale;

@AllArgsConstructor
@Service
public class MessageInfoProvider {

    private final MessageSource messageSource;

    public String getMessage(String[] codes, Object[] arguments, String defaultMessage){
        DefaultMessageSourceResolvable resolvable = new DefaultMessageSourceResolvable(codes,arguments,defaultMessage);
        return messageSource.getMessage(resolvable, Locale.getDefault());
    }

    public String getMessage(String[] codes, Object[] arguments){
        DefaultMessageSourceResolvable resolvable = new DefaultMessageSourceResolvable(codes,arguments);
        return messageSource.getMessage(resolvable, Locale.getDefault());
    }

    public String getMessage(String code){
        DefaultMessageSourceResolvable resolvable = new DefaultMessageSourceResolvable(code);
        return messageSource.getMessage(resolvable,Locale.getDefault());
    }
}
