package br.com.fsrocha.vrcontadigital.application.mapper;

import br.com.fsrocha.vrcontadigital.application.dto.request.TransactionRequest;
import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {

    public static TransactionData toTransactionData(String keyPix, TransactionRequest request, OperationType operationType) {
        var data = new TransactionData(keyPix, request.getValor(), request.getSenha());
        data.setOperationType(operationType);
        return data;
    }

}
