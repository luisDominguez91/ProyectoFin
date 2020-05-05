/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.infrastructure.util;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class Quincena{

    public static LocalDate sumarQuincenas (final LocalDate fechainicial,final int pagarCada){
        LocalDate fechaQuincena=fechainicial;
        for (int i=0;i<pagarCada;i++){
            if(fechaQuincena.getDayOfMonth()<=15){
                fechaQuincena= new org.joda.time.LocalDate(fechaQuincena.getYear(),fechaQuincena.getMonthOfYear(),getLastDayofMoth(fechaQuincena.getMonthOfYear(),fechaQuincena.getYear()));
            }else{
                int mes= fechaQuincena.getMonthOfYear() + 1;
                int anio = fechaQuincena.getYear();
                if(mes == 13){
                    mes = 1;
                    anio = anio + 1 ;
                }
                fechaQuincena= new LocalDate(anio, mes,15);
            }
        }
        return fechaQuincena;
    }
    public static LocalDate restarQuincenas (final LocalDate fechainicial,final int pagarCada){
        LocalDate fechaQuincena=fechainicial;
        for (int i=0;i<pagarCada;i++){
            if (fechaQuincena.getDayOfMonth() <= 15) {
                int anio = fechaQuincena.getYear();
                int mes = fechaQuincena.getMonthOfYear() - 1;
                if (mes==0){
                    anio = anio - 1;
                    mes = 12;
                }
                fechaQuincena = new LocalDate(anio,mes,getLastDayofMoth(mes,anio));
            }else{
                fechaQuincena = new LocalDate(fechaQuincena.getYear(),fechaQuincena.getMonthOfYear(),15);
            }
        }
        return fechaQuincena;
    }
    private static int getLastDayofMoth(int mes, int anio){
        int dias=0;
        switch (mes) {
            case 4:
            case 6:
            case 9:
            case 11:
                dias=30;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dias=31;
                break;
            case 2:
                if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0))){
                    dias=29;
                }else{
                    dias=28;
                }
                break;
        }
        return dias;
    }

    public static int quincenasEntre (final LocalDate fechainicial,final LocalDate fechafinal){
        int quincenas = 0;
        LocalDate fechaQuincena=fechainicial;
        while(fechaQuincena.isBefore(fechafinal)){
           fechaQuincena=sumarQuincenas(fechaQuincena,1);
           quincenas++;
        }
        return quincenas;
    }
}