package edu.tamu.scholars.middleware.discovery.response;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({
    "number",
    "size",
    "numberOfElements",
    "first",
    "last",
    "pageable",
    "sort",
    "totalPages",
    "totalElements"
})
public class DiscoveryPage<T> extends PageImpl<T> {

    private static final long serialVersionUID = -3738016109644028337L;

    public DiscoveryPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageInfo getPage() {
        return PageInfo.from(getSize(), getTotalElements(), getTotalPages(), getNumber());
    }

    public static <T> DiscoveryPage<T> from(List<T> content, Pageable pageable, long total) {
        return new DiscoveryPage<T>(content, pageable, total);
    }

    public static class PageInfo {

        private final int size;

        private final long totalElements;

        private final int totalPages;

        private final int number;

        public PageInfo(int size, long totalElements, int totalPages, int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getNumber() {
            return number;
        }

        public static PageInfo from(int size, long totalElements, int totalPages, int number) {
            return new PageInfo(size, totalElements, totalPages, number);
        }

    }

}
