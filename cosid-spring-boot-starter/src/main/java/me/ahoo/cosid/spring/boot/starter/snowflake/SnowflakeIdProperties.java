/*
 * Copyright [2021-2021] [ahoo wang <ahoowang@qq.com> (https://github.com/Ahoo-Wang)].
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ahoo.cosid.spring.boot.starter.snowflake;

import me.ahoo.cosid.CosId;
import me.ahoo.cosid.snowflake.DefaultClockBackwardsSynchronizer;
import me.ahoo.cosid.snowflake.MillisecondSnowflakeId;
import me.ahoo.cosid.snowflake.machine.LocalMachineStateStorage;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

/**
 * @author ahoo wang
 */
@ConfigurationProperties(prefix = SnowflakeIdProperties.PREFIX)
public class SnowflakeIdProperties {
    public final static String PREFIX = CosId.COSID_PREFIX + "snowflake";

    private boolean enabled = false;
    private long epoch = CosId.COSID_EPOCH;
    private Machine machine;
    private ClockBackwards clockBackwards;
    private IdDefinition share;
    private Map<String, IdDefinition> provider;

    public SnowflakeIdProperties() {
        share = new IdDefinition();
        machine = new Machine();
        clockBackwards = new ClockBackwards();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ClockBackwards getClockBackwards() {
        return clockBackwards;
    }

    public void setClockBackwards(ClockBackwards clockBackwards) {
        this.clockBackwards = clockBackwards;
    }

    public IdDefinition getShare() {
        return share;
    }

    public void setShare(IdDefinition share) {
        this.share = share;
    }

    public Map<String, IdDefinition> getProvider() {
        return provider;
    }

    public void setProvider(Map<String, IdDefinition> provider) {
        this.provider = provider;
    }

    public static class Machine {
        private Boolean stable;

        private Integer port;
        private String instanceId;

        private int machineBit = MillisecondSnowflakeId.DEFAULT_MACHINE_BIT;

        private StateStorage stateStorage;
        private Distributor distributor;

        public Machine() {
            stateStorage = new StateStorage();
            distributor = new Distributor();
        }

        public Boolean getStable() {
            return stable;
        }

        public void setStable(Boolean stable) {
            this.stable = stable;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

        public int getMachineBit() {
            return machineBit;
        }

        public void setMachineBit(int machineBit) {
            this.machineBit = machineBit;
        }

        public StateStorage getStateStorage() {
            return stateStorage;
        }

        public void setStateStorage(StateStorage stateStorage) {
            this.stateStorage = stateStorage;
        }

        public Distributor getDistributor() {
            return distributor;
        }

        public void setDistributor(Distributor distributor) {
            this.distributor = distributor;
        }

        public static class StateStorage {

            private boolean enabled = true;
            private Local local;

            public StateStorage() {
                this.local = new Local();
            }

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public Local getLocal() {
                return local;
            }

            public void setLocal(Local local) {
                this.local = local;
            }

            public static class Local {

                private String stateLocation = LocalMachineStateStorage.DEFAULT_STATE_LOCATION_PATH;

                public String getStateLocation() {
                    return stateLocation;
                }

                public void setStateLocation(String stateLocation) {
                    this.stateLocation = stateLocation;
                }
            }
        }

        public static class Distributor {
            public static final String TYPE = PREFIX + ".machine.distributor.type";
            private Type type = Type.MANUAL;
            private Manual manual;
            private Redis redis;

            public Distributor() {
                this.redis = new Redis();
            }

            public Type getType() {
                return type;
            }

            public void setType(Type type) {
                this.type = type;
            }

            public Manual getManual() {
                return manual;
            }

            public void setManual(Manual manual) {
                this.manual = manual;
            }

            public Redis getRedis() {
                return redis;
            }

            public void setRedis(Redis redis) {
                this.redis = redis;
            }

            public enum Type {
                MANUAL,
                STATEFUL_SET,
                REDIS
            }
        }

        public static class Manual {

            private Integer machineId;

            public Integer getMachineId() {
                return machineId;
            }

            public void setMachineId(Integer machineId) {
                this.machineId = machineId;
            }
        }

        public static class Redis {

            private Duration timeout = Duration.ofSeconds(1);

            public Duration getTimeout() {
                return timeout;
            }

            public void setTimeout(Duration timeout) {
                this.timeout = timeout;
            }
        }
    }

    public static class IdDefinition {
        private boolean clockSync = true;
        private boolean friendly = true;
        private TimestampUnit timestampUnit = TimestampUnit.MILLISECOND;
        private long epoch;
        private int timestampBit = MillisecondSnowflakeId.DEFAULT_TIMESTAMP_BIT;
        private Integer machineBit;
        private int sequenceBit = MillisecondSnowflakeId.DEFAULT_SEQUENCE_BIT;

        public boolean isClockSync() {
            return clockSync;
        }

        public void setClockSync(boolean clockSync) {
            this.clockSync = clockSync;
        }

        public boolean isFriendly() {
            return friendly;
        }

        public void setFriendly(boolean friendly) {
            this.friendly = friendly;
        }

        public TimestampUnit getTimestampUnit() {
            return timestampUnit;
        }

        public void setTimestampUnit(TimestampUnit timestampUnit) {
            this.timestampUnit = timestampUnit;
        }

        public long getEpoch() {
            return epoch;
        }

        public void setEpoch(long epoch) {
            this.epoch = epoch;
        }

        public int getTimestampBit() {
            return timestampBit;
        }

        public void setTimestampBit(int timestampBit) {
            this.timestampBit = timestampBit;
        }

        public Integer getMachineBit() {
            return machineBit;
        }

        public void setMachineBit(Integer machineBit) {
            this.machineBit = machineBit;
        }

        public int getSequenceBit() {
            return sequenceBit;
        }

        public void setSequenceBit(int sequenceBit) {
            this.sequenceBit = sequenceBit;
        }

        public enum TimestampUnit {
            SECOND,
            MILLISECOND
        }
    }

    public static class ClockBackwards {

        private int spinThreshold = DefaultClockBackwardsSynchronizer.DEFAULT_SPIN_THRESHOLD;
        private int brokenThreshold = DefaultClockBackwardsSynchronizer.DEFAULT_BROKEN_THRESHOLD;

        public int getSpinThreshold() {
            return spinThreshold;
        }

        public void setSpinThreshold(int spinThreshold) {
            this.spinThreshold = spinThreshold;
        }

        public int getBrokenThreshold() {
            return brokenThreshold;
        }

        public void setBrokenThreshold(int brokenThreshold) {
            this.brokenThreshold = brokenThreshold;
        }
    }
}