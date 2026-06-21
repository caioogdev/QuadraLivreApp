package br.com.infnet.catalogoservice.shared.dtos;

import java.util.List;

public record PaginationDTO<T>(
        long total,
        List<T> data,
        int page,
        int perPage,
        boolean hasNext,
        boolean hasPrev
) {}
