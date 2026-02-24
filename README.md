# Assignment 3 — Q3 (FlowRow / FlowColumn): Tag Browser + Filters

## Overview
This project implements **Q3: Tag Browser + Filters** using **Jetpack Compose + Material 3**.

The screen includes:
- A **FlowRow** of tags (chips) that wrap to the next line.
- A **FlowColumn** used for a different purpose: a **Selected Tags** area that can wrap into multiple columns when space is limited.
- Simple state: tapping chips adds/removes them from **Selected Tags**.

## Requirements Checklist

### Layout
- ✅ **FlowRow** used for a dynamic list of tags that wrap across lines.
- ✅ **FlowColumn** used in a separate section for a different purpose (Selected Tags list).
- ✅ **Selected Tags** area updates when user taps chips.

### Material 3 Components
- ✅ **FilterChip** (required)
- ✅ Additional M3 components (4+):
  - Scaffold
  - TopAppBar
  - Card
  - Button
  - Divider
  - SnackbarHost
  - Text (used throughout)

### Modifiers
- ✅ Consistent spacing with `Arrangement.spacedBy(...)` (FlowRow, FlowColumn, Column)
- ✅ Responsive sizing with `fillMaxWidth()` and `padding(...)`
- ✅ Visual selected state change (FilterChip `selected` + border + leading check icon)

## Screenshot
<img width="373" height="808" alt="Screenshot 2026-02-23 204842" src="https://github.com/user-attachments/assets/d632dcc2-3e0f-47b7-9063-f06368009235" />

## AI Disclosure
I used AI assistance (ChatGPT) to help me debug the emulator that wasn't working because of FlowRow/FlowColumn. I reviewed, edited, and tested the final implementation in Android Studio.
