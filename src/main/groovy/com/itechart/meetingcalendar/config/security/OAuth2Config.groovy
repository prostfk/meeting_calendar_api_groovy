//package com.itechart.meetingcalendar.config.security
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//
//@Configuration
//class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//    private String clientId = "clientIdTest123"
//    private String clientSecret = "clientSecret123"
//    private String privateKey = "privateKey123"
//    private String publicKey = "publicKey123"
//
//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager
//
//    @Bean
//    JwtAccessTokenConverter tokenEnhancer() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter()
//        converter.setSigningKey(privateKey)
//        converter.setVerifierKey(publicKey)
//        return converter
//    }
//    @Bean
//    JwtTokenStore tokenStore() {
//        return new JwtTokenStore(tokenEnhancer())
//    }
//    @Override
//    void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
//                .accessTokenConverter(tokenEnhancer())
//    }
//    @Override
//    void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
//    }
//    @Override
//    void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient(clientId).secret(clientSecret).scopes("read", "write")
//                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
//                .refreshTokenValiditySeconds(20000)
//
//    }
//
//}
