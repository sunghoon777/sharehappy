package sharehappy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"sharehappy.controller.donation","sharehappy.controller.main"})
public class ControllerConfig {
}
