package br.com.spedison.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.NumberFormat;
import java.util.Locale;

public class IntegerUtils {
    final static private Logger log = LoggerFactory.getLogger(IntegerUtils.class);
    final static Locale localeBrasil = Locale.of ("pt", "BR");
    final static NumberFormat nf = NumberFormat.getInstance(localeBrasil);


    public static Integer parseInt(String str){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            log.error("Erro ao converter string para inteiro: " + str, e);
            return -1;
        }
    }

    public static String formatInteger(Integer value){
        return formatInteger(value, 0);
    }

    public static String formatInteger(Integer value, int casasDecimais){
        nf.setMaximumFractionDigits(casasDecimais);
        nf.setMinimumIntegerDigits(casasDecimais);
        if(value == null){
            return "";
        }
        return nf.format(value) ;
    }

}
