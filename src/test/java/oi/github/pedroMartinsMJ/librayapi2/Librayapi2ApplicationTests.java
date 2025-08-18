package oi.github.pedroMartinsMJ.librayapi2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Import(TestConfig.class) // Importa nossa configuração
class Librayapi2ApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("✅ Testes rodando com PostgreSQL em container!");
	}
}
