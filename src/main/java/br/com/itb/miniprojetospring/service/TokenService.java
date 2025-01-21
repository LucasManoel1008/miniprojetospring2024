package br.com.itb.miniprojetospring.service;

import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final Map<String, LocalDateTime> tokenStorage = new ConcurrentHashMap<>();

    public void adicionarToken(String token){
        tokenStorage.put(token, LocalDateTime.now());
    }

    @Scheduled(fixedRate = 60000)
    public void limparToken(){
        Iterator<Map.Entry<String, LocalDateTime>> interator = tokenStorage.entrySet().iterator();
        while (interator.hasNext()){
            Map.Entry<String, LocalDateTime> entry = interator.next();
            if(LocalDateTime.now().isAfter(entry.getValue().plusMinutes(10))){
                interator.remove();
                System.out.println("Token Removido: " + entry.getKey());
            }
        }
    }
}
