package com.pulse.model.constant;

/**
 *
 * @author befast
 */
public enum Filter {        
        ALL         ("Все",             (byte) 0),
        //EVENT_BASED ("По приходу",      (byte) 1),
        //REGISTERED  ("По записи",       (byte) 2),
        NOT_OBSERVED("Осмотренные", (byte) 3),
        OBSERVED    ("Неосмотренные",   (byte) 4);
        
        private Filter(String name, byte id) {
            this.name = name;
            this.id = id;
        }
        
        private String name;
        private byte id;
        
        public String getName() {
            return this.name;
        }        
    }
