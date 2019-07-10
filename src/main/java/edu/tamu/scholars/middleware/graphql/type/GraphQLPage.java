package edu.tamu.scholars.middleware.graphql.type;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;

import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType(name = "Page")
public class GraphQLPage<T> implements Iterable<T> {

    private final List<T> content;

    private final PageInfo page;

    public GraphQLPage(List<T> content, PageInfo page) {
        this.content = content;
        this.page = page;
    }

    public static <T> GraphQLPage<T> from(Page<T> page) {
        return new GraphQLPage<T>(page.getContent(), PageInfo.from(page));
    }

    public static <T> GraphQLPage<T> from(List<T> content, PageInfo page) {
        return new GraphQLPage<T>(content, page);
    }

    public List<T> getContent() {
        return content;
    }

    public PageInfo getPage() {
        return page;
    }

    @GraphQLType(name = "PageInfo")
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

        public static PageInfo from(Page<?> page) {
            return new PageInfo(page.getSize(), page.getTotalElements(), page.getTotalPages(), page.getNumber());
        }

        public static PageInfo from(int size, long totalElements, int totalPages, int number) {
            return new PageInfo(size, totalElements, totalPages, number);
        }

    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

}