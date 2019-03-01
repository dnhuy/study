package com.itsol.smartoffice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Function;

@Component
public class DtoManager {
	private ModelMapper modelMapper;

	public DtoManager(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public DtoManager() {
	}

	public <T, D> void convertToListDto(List<T> tList, List<D> dList, Class<D> d) {
		for (T item : tList) {
			dList.add(modelMapper.map(item, d));
		}
	}

	public <T, D> void convertToDto(T t, D d) {
		modelMapper.map(t, d);
	}

	public <T, D> Page<D> convertToPageDto(Page<T> tPage, Class<D> d) {
		return tPage.map(new Function<T, D>() {
			@Override
			public D apply(T t) {
				try {
					D item = d.newInstance();
					modelMapper.map(t, item);
					return item;
				} catch (Exception e) {
					return null;
				}

			}
		});
	}
}