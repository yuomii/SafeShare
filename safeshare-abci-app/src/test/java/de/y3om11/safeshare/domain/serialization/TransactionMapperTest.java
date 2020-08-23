package de.y3om11.safeshare.domain.serialization;

import de.y3om11.safeshare.domain.objects.dnsentry.DnsEntry;
import de.y3om11.safeshare.domain.objects.dnsentry.DnsEntryBuilder;
import de.y3om11.safeshare.domain.objects.filetransfer.FileTransfer;
import de.y3om11.safeshare.domain.objects.filetransfer.FileTransferBuilder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TransactionMapperTest {

    @Test
    void testFileTransfer(){
        FileTransfer f = new FileTransferBuilder()
                .withTransferId("id")
                .withFrom("from")
                .withTo("to")
                .withTimestamp(1L)
                .build();
        TransactionMapper transactionMapper = new TransactionMapper();
        String txString = transactionMapper.createTx(f).get();
        System.out.println(txString);
        FileTransfer f2 = (FileTransfer) transactionMapper.getTxFromHexString(txString).get();
    }

    @Test
    void testDomainEntry(){
        DnsEntry dnsEntry = new DnsEntryBuilder()
                .withDnsEntry(UUID.randomUUID().toString())
                .withPublicKeyBytes("PubkeyBytes".getBytes())
                .withDomainName("MyDomain")
                .build();
        TransactionMapper transactionMapper = new TransactionMapper();
        String txString = transactionMapper.createTx(dnsEntry).get();
        System.out.println(txString);
    }

}