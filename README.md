# MobilBankGroupTask

Small Android sample app built with Kotlin + Jetpack Compose for MultiBankGroup (However I mistakenly named project MobilBankGroup :D ).

The app shows a live stock feed from a WebSocket echo server and lets you open a details screen for each symbol.

---

## What this app does

- Shows a stock list in real time (updates every 2 seconds)
- Sorts symbols by current price (highest first)
- Shows up/down indicator for each symbol
- Flashes the 'price text' on change:
  - green on increase
  - red on decrease
- Opens symbol details when you tap a row
- Supports deep link:
  - `stocks://symbol/{symbol}`

---

## Tech stack

- Kotlin
- Jetpack Compose
- Navigation Compose
- Hilt (DI)
- Coroutines + Flow
- OkHttp WebSocket

---

## Project structure

This project follows a clean architecture style with modules:

- `app`  
  App entry point, activity, nav host, screen enum, theme.

- `domain`  
  Pure business layer: models, repository contracts, use cases.

- `data`  
  Repository implementation, stock catalog, WebSocket handling, DTO mapping.

- `di`  
  Hilt modules (repository binding, dispatcher, network client).

- `presentation`  
  UI state/action/intent/reducer/viewmodel + screens.

- `uiKit`  
  Reusable UI components (for now, stock card).

---

## Main screens

### Feed

- Top bar with connection indicator + Start/Stop button
- `LazyColumn` of stock rows
- Rows are clickable and navigate to details

### Symbol Details

- Reads selected symbol from `SavedStateHandle`
- Observes live stock stream and updates fields in place

---

## WebSocket behavior

- Endpoint: `wss://ws.postman-echo.com/raw`
- Bulk payload is sent every 2 seconds
- Echoed payload is parsed and pushed to UI stream
- Auto reconnect is enabled after socket failures
- Feed start/stop uses request counting to avoid duplicate socket jobs

---

### Requirements for Build & run

- Android Studio (latest stable recommended)
- JDK 11

---

## Notes
- Prices are simulated and echoed through the socket service.
- `uiKit` is intentionally minimal and can be extended later for shared UI patterns.
