# BackendBlogApplication
SpringBoot,Spring Security,MySQL,JPA
Exception Handling with proper Api Response
Pagination & Sorting done via using PagingAndSortingRepository.
Latest version of spring security in which webSecurityConfigurerAdapter is deprecated is resolved.
Admin has access to all apis and has to login first and then pass on the generated JWT token as header in each api calling in order to access that api.
Normal user do not have access to specific apis such as category related apis other than fetch,user related apis other than create
