package com.thrinod.web.rest;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.thrinod.domain.*;
import com.thrinod.repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    TICKRepository tickRepository;

    @Autowired
    TickFuturesRepository tickFuturesRepository;

    @Autowired
    TickOptionRepository tickOptionRepository;

    @Autowired
    TickStockRepository tickStockRepository;

    @Autowired
    TickIndexRepository tickIndexRepository;

    @GetMapping("/loadCompleteTicks")
    public ResponseEntity<String> loadTicks() {
        List<TICK> tickList = new ArrayList<>();
        List<TickOption> tickOptions = new ArrayList<>();
        List<TickStock> tickStocks = new ArrayList<>();
        List<TickIndex> tickIndexList = new ArrayList<>();
        List<TickFutures> tickFutures = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("upstox_complete_nse_data.csv"));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                TICK t = new TICK();
                t.setInstrumentKey(values[0]);
                t.setExchangeToken(values[1]);
                t.setTradingSymbol(values[2]);
                t.setName(values[3]);
                t.setLastPrice(values[4]);
                t.setExpiry(values[5]);
                t.setStrike(values[6]);
                t.setTickSize(values[7]);
                t.setLotSize(values[8]);
                t.setInstrumentType(values[9]);
                t.setOptionType(values[10]);
                t.setExchange(values[11]);
                if("CE".equalsIgnoreCase(t.getInstrumentType()) || "PE".equalsIgnoreCase(t.getInstrumentType()) || t.getInstrumentType().startsWith("OPT")){
                    TickOption tickOption = new TickOption();
                    BeanUtils.copyProperties(t,tickOption);
                    tickOptions.add(tickOption);
                }else if("EQ".equalsIgnoreCase(t.getInstrumentType()) || "EQUITY".equalsIgnoreCase(t.getInstrumentType())) {
                    TickStock tickStock = new TickStock();
                    BeanUtils.copyProperties(t,tickStock);
                    tickStocks.add(tickStock);
                }else if("FUT".equalsIgnoreCase(t.getInstrumentType()) ||  t.getInstrumentType().startsWith("FUT")) {
                    TickFutures tickFuture = new TickFutures();
                    BeanUtils.copyProperties(t,tickFuture);
                    tickFutures.add(tickFuture);
                }else if("INDEX".equalsIgnoreCase(t.getInstrumentType()) || "EQUITY".equalsIgnoreCase(t.getInstrumentType())) {
                    TickIndex tickIndex = new TickIndex();
                    BeanUtils.copyProperties(t,tickIndex);
                    tickIndexList.add(tickIndex);
                }else {
                    tickList.add(t);
                }
            }

        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tickRepository.saveAll(tickList);
        tickFuturesRepository.saveAll(tickFutures);
        tickIndexRepository.saveAll(tickIndexList);
        tickOptionRepository.saveAll(tickOptions);
        tickStockRepository.saveAll(tickStocks);
        return ResponseEntity.ok("Done");
    }
}
