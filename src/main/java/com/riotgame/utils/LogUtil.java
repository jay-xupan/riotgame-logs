package com.riotgame.utils;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.riotgame.model.LogVo;
import com.riotgame.model.ReponseSummary;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class LogUtil {


    public static List<ReponseSummary> percentile() throws IOException {
        File sourceFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "log/2018-13-10.log");
        ArrayList<LogVo> logs = getLogs(sourceFile);
        Map<String, List<LogVo>> logMap = logs.parallelStream().collect(Collectors.groupingBy(LogVo::getUrl));

        List<ReponseSummary> reponseSummaries = new ArrayList<>();
        logMap.entrySet().stream().sorted(Comparator.comparingInt(value -> value.getValue().size()))
                .forEach(stringListEntry -> {
                    String url = stringListEntry.getKey();
                    List<Long> sorted = stringListEntry.getValue()
                            .parallelStream()
                            .map(LogVo::getTimeconsuming)
                            .sorted(Long::compareTo)
                            .collect(Collectors.toList());
                    reponseSummaries.add(calculate(url, sorted));
                });
        return reponseSummaries;
    }

    private static ArrayList<LogVo> getLogs(File file) throws IOException {
        ArrayList<LogVo> logs = Lists.newArrayList();
        Files.asCharSource(file, Charset.forName("UTF-8")).readLines(new LineProcessor<String>() {

            @Override
            public String getResult() {
                return null;
            }

            @Override
            public boolean processLine(String line) {
                String[] arrLine = line.split(" ");
                String url = arrLine[3];
                String queryTime = arrLine[5];
                LogVo log = new LogVo();
                log.setTimeconsuming(Long.parseLong(queryTime));
                log.setUrl(url.split("\\?")[0]);

                logs.add(log);
                return true;
            }
        });
        return logs;
    }


    private static ReponseSummary calculate(String url, List<Long> timeDurations) {
        int count = timeDurations.size();
        double tp90 = count * 0.9 - 1;
        double tp95 = count * 0.95 - 1;
        double tp99 = count * 0.99 - 1;

        DoubleSummaryStatistics doubleSummaryStatistics = timeDurations.parallelStream().mapToDouble(Long::longValue).summaryStatistics();
        ReponseSummary rs = new ReponseSummary(
                url,
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                timeDurations.get((int) Math.ceil(tp90)),
                timeDurations.get((int) Math.ceil(tp95)),
                timeDurations.get((int) Math.ceil(tp99)));
        return rs;
    }
}
