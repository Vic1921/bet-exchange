# AutoBet: Automated Martingale Betting System

AutoBet is an automated betting system designed to execute a Martingale-based betting strategy on football draws in the Romanian top division. The system continuously runs throughout the season with minimal manual intervention. Its main goal is to automatically place bets on draw outcomes at odds of 3.00, manage your bankroll according to the doubling strategy, and provide real-time visual insights via a user-friendly dashboard.

---

## Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Architecture](#architecture)
    - [Backend (Java Spring Boot)](#backend-java-spring-boot)
    - [Frontend (Angular Dashboard)](#frontend-angular-dashboard)
    - [Bet Placement Engine](#bet-placement-engine)
- [Workflow](#workflow)
- [Setup and Deployment](#setup-and-deployment)
- [Risk Management & Considerations](#risk-management--considerations)
- [Final Notes](#final-notes)

---

## Overview

AutoBet leverages a classic Martingale betting strategy where the stake is doubled after every loss. By targeting football matches with draw odds of 3.00, the system aims to recoup losses and secure a modest profit once a draw occurs. The strategy is designed to work automatically during the entire season without the need for constant manual oversight. The only points of intervention are:

- **Start/Stop Mechanism:** Control the betting process via the frontend dashboard.
- **Error Alerts:** Receive notifications via email/SMS if there are issues like bet placement errors or account limits.

This project is implemented using Java Spring Boot for the backend, Angular for the dashboard, and a bet placement module that can either integrate directly with betting agencies via an API or automate interactions using Selenium.

---

## Key Features

- **Automated Betting:** The system monitors upcoming matches, applies the Martingale strategy, and places bets automatically.
- **Continuous Season Operation:** Designed to run for the entire football season without manual intervention.
- **Dynamic Bankroll Management:** Tracks your bankroll in real time, ensuring that betting operations continue only if funds are sufficient.
- **Dashboard Visualization:** A web-based dashboard provides real-time statistics on profit, bet history, and current bankroll. It also allows you to manually start or stop the betting mechanism.
- **Flexible Bet Placement:** Integrates either with official betting agency APIs (if available) or uses Selenium for web automation.
- **Alert System:** Sends email or SMS notifications when critical issues occur, such as bet placement failures or bankroll thresholds being breached.

---

## Architecture

### Backend (Java Spring Boot)

- **Technology Stack:** Java, Spring Boot, Maven, Spring Data JPA.
- **Responsibilities:**
    - **Core Betting Logic:** Implements the Martingale betting strategy and calculates next stakes.
    - **Data Persistence:** Uses a relational database (H2 for development, PostgreSQL for production) to store match data, bets, bankroll history, and configuration settings.
    - **REST API:** Exposes endpoints to start/stop betting rounds, update bet results, and provide data for the dashboard.
    - **Scheduled Tasks:** Automates the betting process by continuously checking for upcoming matches and initiating bets based on predefined criteria.

### Frontend (Angular Dashboard)

- **Technology Stack:** Angular, HTML, CSS, and JavaScript/TypeScript.
- **Responsibilities:**
    - **Visualization:** Presents a comprehensive dashboard showing profit graphs, betting history, and current bankroll.
    - **User Controls:** Allows manual intervention to start or stop the betting process.
    - **Real-Time Updates:** Receives live updates from the backend (using REST APIs and/or WebSockets) to keep the dashboard current.

### Bet Placement Engine

- **Flexible Integration Options:**
    - **API Integration:** If the betting agency provides an official API, the system will use it for fast, secure bet placement.
    - **Selenium Automation:** If an API is not available, Selenium WebDriver will automate the process of logging into the betting site and placing bets.
- **Abstraction:** A common `BetPlacer` interface abstracts the underlying method (API or Selenium) to allow for easy switching and future enhancements.

---

## Workflow

1. **Automated Match Monitoring:**
    - The backend continuously scans for upcoming matches that meet the draw odds criteria.
2. **Betting Execution:**
    - When a qualifying match is identified, the system calculates the required stake using the Martingale doubling strategy.
    - A bet is placed automatically using the selected bet placement engine.
3. **Result Processing:**
    - Once the match result is known, the system updates the bet status (win/loss).
    - In case of a win, winnings are credited to the bankroll, and the loss streak counter resets.
    - In case of a loss, the loss streak counter increments, and the next bet is calculated accordingly.
4. **Dashboard Updates:**
    - All bet activities, bankroll changes, and profit/loss metrics are updated in real time on the dashboard.
5. **Error Handling & Alerts:**
    - If bet placement fails or if there are critical errors (e.g., insufficient funds), alerts are sent via email or SMS.

---

## Setup and Deployment

### Development Environment

- **Backend:**
    - Use Spring Initializr to generate a Maven-based Spring Boot project.
    - Configure your `application.properties` for development (e.g., H2 database).

- **Frontend:**
    - Set up an Angular project with a focus on building a dashboard.
    - Use REST API endpoints provided by the backend to fetch live data.

- **Bet Placement:**
    - Implement the `BetPlacer` interface with two implementations:
        - **APIBetPlacer:** For betting agencies offering an API.
        - **SeleniumBetPlacer:** For automating bets on websites using Selenium WebDriver.

### Deployment

- **Backend Deployment:**
    - Package the Spring Boot application using Maven (`mvn clean package`) and deploy on a cloud platform like AWS, DigitalOcean, or Heroku.
    - For production, configure the application to use a robust database like PostgreSQL.

- **Frontend Deployment:**
    - Deploy the Angular application on platforms like Netlify, Vercel, or Firebase Hosting.
    - Ensure proper CORS settings are configured to communicate with the backend.

- **Scheduling and Automation:**
    - Use Spring's scheduling capabilities to trigger automated betting rounds without manual intervention.
    - The system is designed to run continuously throughout the season, with the only manual control being the start/stop command from the dashboard.

---

## Risk Management & Considerations

- **Long Losing Streaks:**
    - The Martingale strategy may require a deep bankroll during extended periods without a win. The system monitors bankroll levels and stops betting if funds are insufficient.

- **Betting Agency Constraints:**
    - Bookmakers may impose bet limits or detect automated behavior. The system uses human-like delays (in Selenium mode) and integrates with official APIs when possible to mitigate these risks.

- **Automated Operation:**
    - The system is engineered to operate autonomously over the entire season. Minimal intervention is required, with the dashboard providing all necessary controls and notifications.

- **Error Handling:**
    - Automatic alerts (via email/SMS) will notify you of any critical issues such as bet placement failures or unexpected errors.

---

## Final Notes

AutoBet is designed to be a fully autonomous betting system that runs for the entire season with minimal intervention. You, as the operator, will have oversight via a comprehensive dashboard that visualizes your profit, bet history, and current bankroll. The only manual interventions needed are to start or stop the mechanism and to react to any critical alerts from the system.

This project architecture provides a robust and scalable foundation for automated betting using a Martingale strategy, with clear separations between the backend logic, frontend controls, and bet placement mechanisms. Enjoy building and refining your automated betting strategy!

---
