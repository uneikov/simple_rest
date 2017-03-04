package com.uran.domain.projections;

import com.uran.domain.Horse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "horseNameView", types = {Horse.class})
public interface HorseNameView {
    @Value("#{target.name}:#{target.ruName}")
    String getFullName();
}
