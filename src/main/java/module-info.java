/**
 * Module descriptor for tk-log-utils.
 * 
 * This module provides extended logging utilities with thread safety optimizations.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 * @since 2.0.5
 */
module tk.log.utils {
    requires transitive org.slf4j;

    exports ru.spb.tksoft.utils.log;
}
