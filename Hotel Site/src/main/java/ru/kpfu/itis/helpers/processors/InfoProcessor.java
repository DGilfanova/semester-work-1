package ru.kpfu.itis.helpers.processors;

public interface InfoProcessor<T,K> {
    T process(K form);
}
