package com.example.breysi.biblio_virtual;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Calendario {

    public static Calendar StringaFecha(String fecha) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = dateFormat.parse(fecha);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        return cal;
    }

    public static Calendar reservaMes(Calendar c1) {
        c1.add(Calendar.DATE, 30);
        return c1;
    }

    public static String FechaaString(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;
        Date d2 = calendar.getTime();
        fecha = dateFormat.format(d2);
        return fecha;

    }


}


