package kazpost.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PushApplication {

//	private final KafkaAdmin kafkaAdmin;
//
//	@Autowired
//	public PushApplication(KafkaAdmin kafkaAdmin) {
//		this.kafkaAdmin = kafkaAdmin;
//	}
//
//	@EventListener(ContextRefreshedEvent.class)
//	public void onStartup() {
//		// Удаляем все сообщения из очереди повторной обработки
//		deleteTopic("ios-push-retry");
//	}
//
//	private void deleteTopic(String topicName) {
//		Properties properties = new Properties();
//		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAdmin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
//		try (AdminClient adminClient = AdminClient.create(properties)) {
//			DeleteTopicsResult result = adminClient.deleteTopics(Collections.singleton(topicName));
//			result.all().get(); // Блокируем, пока операция не завершится
//			System.out.println("Тема " + topicName + " успешно удалена.");
//		} catch (InterruptedException | ExecutionException e) {
//			System.err.println("Ошибка при удалении темы " + topicName + ": " + e.getMessage());
//		}
//	}

	public static void main(String[] args) {
		SpringApplication.run(PushApplication.class, args);
	}

}
