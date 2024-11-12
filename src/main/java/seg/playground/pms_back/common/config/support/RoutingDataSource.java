package seg.playground.pms_back.common.config.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private final List<String> readKeyList = new ArrayList<>();
    private boolean writeOnly;
    private int readSize;
    private int readIdx;

    public RoutingDataSource(DataSource writeDataSource, List<DataSource> readDataSourceList) throws Exception {
        if (writeDataSource == null) {
            throw new Exception("writeDataSource is empty");
        }

        Map<Object, Object> targetDataSourceMap = new HashMap<>();
        targetDataSourceMap.put("WRITE", new LazyConnectionDataSourceProxy(writeDataSource));

        if (!readDataSourceList.isEmpty()) {
            for (int i = 0; i < readDataSourceList.size(); i++) {
                readKeyList.add("READ_" + i);
                targetDataSourceMap.put("READ_" + i, new LazyConnectionDataSourceProxy(readDataSourceList.get(i)));
            }
            readSize = readKeyList.size();
        } else {
            this.writeOnly = true;
        }

        setTargetDataSources(targetDataSourceMap);
        setDefaultTargetDataSource(targetDataSourceMap.get("WRITE"));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (!writeOnly && TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            if (readIdx + 1 >= readSize) {
                readIdx = -1;
            }

            return readKeyList.get(++readIdx);
        } else {
            return "WRITE";
        }
    }
}
