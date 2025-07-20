package gift.option.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OptionControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient = RestClient.builder().build();

    @Test
    void 상품에_옵션을_추가하는_기능() {
        var url = "http://localhost:" + port + "/api/options/3";
        OptionRequestDto optionRequestDto = new OptionRequestDto("1TB", 999, 500000);

        var response = restClient.post()
                .uri(url)
                .body(optionRequestDto)
                .retrieve()
                .toEntity(OptionResponseDto.class);

        assertAll(
                () -> assertThat(response.getBody().id()).isNotNull(),
                () -> assertEquals(response.getBody().quantity(), 999)
        );
    }


}




