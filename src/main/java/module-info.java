/**
 * Module descriptor for tk-log-utils.
 * 
 * <p>
 * This module provides extended logging utilities with thread safety optimizations.
 * 
 * @author Konstantin Terskikh
 * @since 2.0.5
 */
module ru.spb.tksoft.tk.log.utils {
    requires transitive org.slf4j;

    exports ru.spb.tksoft.utils.log;
}

