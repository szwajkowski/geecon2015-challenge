package pl.allegro.promo.geecon2015.domain.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTransactions {

    private final List<UserTransaction> transactions = new ArrayList<>();

    public UserTransactions(@JsonProperty("transactions") List<UserTransaction> transactions) {
        this.transactions.addAll(transactions);
    }

    public List<UserTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public BigDecimal sumTransactions() {
        BigDecimal sum = null;
        if (containsNoNullAmount()) {
            sum = getTransactions().stream()
                .map(UserTransaction::getAmount)
                .reduce(new BigDecimal(0), (a, b) -> a.add(b));
        }
        return sum;
    }

    private boolean containsNoNullAmount() {
        return getTransactions().stream().filter(t -> t.getAmount() == null).count() == 0;
    }
}
