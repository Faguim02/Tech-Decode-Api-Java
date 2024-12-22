package com.techdecode.blog.view.client.dtos;

public record IpInfoDto(
        String ip,
        String hostname,
        String city,
        String region,
        String country,
        String loc,
        String org,
        String postal,
        String timezone
) {
}
