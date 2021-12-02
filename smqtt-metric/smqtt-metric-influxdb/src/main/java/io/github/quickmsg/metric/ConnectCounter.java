package io.github.quickmsg.metric;

import io.github.quickmsg.common.metric.CounterType;
import io.github.quickmsg.common.metric.MetricBean;
import io.github.quickmsg.common.metric.WholeCounter;

/**
 * @author luxurong
 */
public class ConnectCounter extends WholeCounter {

    public ConnectCounter(MetricBean metricBean) {
        super(metricBean);
    }

    @Override
    public void callMeter(long counter) {
        getMetricBean().getMeterRegistry().gauge(getCounterType().getDesc(), counter);
    }

    @Override
    public CounterType getCounterType() {
        return CounterType.CONNECT;
    }

}