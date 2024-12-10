package org.webrtc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class FixedSizeQueue<T> {
    private final int maxSize;
    private final ArrayDeque<T> queue;

    public FixedSizeQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new ArrayDeque<>();
    }

    public void add(T item) {
        if (queue.size() >= maxSize) {
            queue.removeFirst(); // 移除最旧的项
        }
        queue.addLast(item); // 添加新项
    }

    public boolean areAllElementsEqual() {
        if (queue.isEmpty()) {
            return false;
        }
        T firstElement = queue.getFirst();
        for (T element : queue) {
            if (!element.equals(firstElement)) {
                return false; // 只要有一个不同的元素就返回 false
            }
        }
        return true; // 所有元素相同
    }

    public List<T> getItems() {
        return new ArrayList<>(queue); // 返回不可变列表
    }

}
