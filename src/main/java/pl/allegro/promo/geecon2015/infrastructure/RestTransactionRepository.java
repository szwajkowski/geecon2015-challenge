package pl.allegro.promo.geecon2015.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.promo.geecon2015.domain.transaction.TransactionRepository;
import pl.allegro.promo.geecon2015.domain.transaction.UserTransaction;
import pl.allegro.promo.geecon2015.domain.transaction.UserTransactions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RestTransactionRepository implements TransactionRepository {

    private final String baseUri;

    private final RestTemplate restTemplate;
    
    @Autowired
    public RestTransactionRepository(@Value("${transaction.uri}") String uri, RestTemplate restTemplate) {
        this.baseUri = uri;
        this.restTemplate = restTemplate;
    }
    
    @Override
    public UserTransactions transactionsOf(UUID userId) {
        UserTransactions userTransactions;
        try {
            userTransactions = restTemplate.getForEntity(baseUri + "/transactions/" + userId.toString(), UserTransactions.class).getBody();
        } catch (HttpServerErrorException e) {
            UserTransaction transaction = new UserTransaction(userId, null);
            List<UserTransaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            userTransactions = new UserTransactions(transactions);
        }
        return userTransactions;
    }
}
