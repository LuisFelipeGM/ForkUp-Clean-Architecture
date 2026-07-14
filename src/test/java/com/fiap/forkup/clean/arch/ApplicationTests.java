package com.fiap.forkup.clean.arch;

import com.fiap.forkup.clean.arch.controller.AbstractControllerIT;
import com.fiap.forkup.clean.arch.controller.BaseControllerIT;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.mockStatic;


class ApplicationTests {

	@Test
	void deveExecutarMainDaAplicacao() {

		try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

			Application.main(new String[]{});

			mocked.verify(() ->
					SpringApplication.run(Application.class, new String[]{})
			);
		}
	}

}
