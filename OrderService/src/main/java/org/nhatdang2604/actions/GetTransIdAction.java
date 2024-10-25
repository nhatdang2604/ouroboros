package org.nhatdang2604.actions;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetTransIdAction{

    /**
     * @return transaction id as uuid v4
     **/
    public String exec() {
        UUID transId = UUID.randomUUID();
        return transId.toString();
    }
}
