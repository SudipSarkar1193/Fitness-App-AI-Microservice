package com.fitness.AiService.services;

import com.fitness.AiService.models.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityMessageListener {

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(Activity activity){
        log.info("Received activity with activity-uuid: {}", activity.getActivityUuid());
        System.out.println();
        System.out.println();
        System.out.println("Processing activity for AI recommendations...");
        System.out.println("Received activity: " + activity.getActivityUuid() + "name of activity: " + activity.getName() + " type of activity: " + activity.getName());
        System.out.println();
    }
}
