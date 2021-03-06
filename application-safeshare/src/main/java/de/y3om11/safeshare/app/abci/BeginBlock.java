package de.y3om11.safeshare.app.abci;

import com.github.jtendermint.jabci.api.IBeginBlock;
import com.github.jtendermint.jabci.types.RequestBeginBlock;
import com.github.jtendermint.jabci.types.ResponseBeginBlock;
import jetbrains.exodus.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BeginBlock implements IBeginBlock {

    private final static Logger log = LoggerFactory.getLogger(BeginBlock.class);

    @Override
    public ResponseBeginBlock requestBeginBlock(final RequestBeginBlock requestBeginBlock) {
        log.info(String.format("Begin Block with new hash %s and height %s",
                HexUtil.byteArrayToString(requestBeginBlock.getHash().toByteArray()), requestBeginBlock.getHeader().getHeight()));
        return ResponseBeginBlock.newBuilder().build();
    }
}
