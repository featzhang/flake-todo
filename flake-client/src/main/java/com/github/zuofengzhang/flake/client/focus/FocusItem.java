package com.github.zuofengzhang.flake.client.focus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FocusItem {
    private boolean sit;
    private boolean finished;
    private boolean interruptItem;
    private String title;
}
