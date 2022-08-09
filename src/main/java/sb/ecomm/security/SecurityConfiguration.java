package sb.ecomm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.css",
                        "/*.js",
                        "#/**",
                        "/api/v1/products/**",
                        "/images/**",
                        "/api/v1/categories/**",
                        "/favicon.ico").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/api/v1/users",
                        "/api/v1/auth/login",
                        "/api/v1/auth/forgotpassword",
                        "/api/v1/auth/refresh",
                        "/api/v1/orders/checkout",
                        "/api/v1/orders/webhook"
                ).permitAll()
                .antMatchers(HttpMethod.PATCH,
                        "/api/v1/auth/verify/*",
                        "/api/v1/auth/resetpassword"
                        ).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers()
                .contentSecurityPolicy(" default-src 'none'; " +
                        "script-src 'self' https://checkout.stripe.com https://js.stripe.com; " +
                        "connect-src 'self' https://api.stripe.com https://checkout.stripe.com; " +
                        "img-src 'self' https://*.stripe.com; " +
                        "style-src 'self' https://fonts.googleapis.com https://rsms.me/inter/inter.css; " +
                        "frame-src https://checkout.stripe.com https://js.stripe.com https://hooks.stripe.com; " +
                        "font-src https://fonts.gstatic.com " +
                        "frame-ancestors 'self'; " +
                        "form-action 'self';");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
