# Yong Shin's Project Portfolio Page

## Overview
Budget Tracker is a command-line app that helps users manage their money. Users can track how much they earn, spend, and save using simple text commands. The app includes a money summary feature and alerts users when their funds get too low.

## Summary of Contributions

### [Code Contributed (Reposense Link)](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=yshinprograms&breakdown=true)

### Features I Built

#### Money Management Core
* **Financial Summary System**: 
  * Built the `Summary` class that tracks all money coming in and going out
  * Created a system that makes sure users enter valid amounts (no negative numbers)
  * Added safeguards to prevent impossible situations like spending more than you have
  * Made sure the system works reliably even with many operations happening

* **Summary Display**: 
  * Created the `SummaryDisplay` class that shows financial information in an easy-to-read format
  * Designed a clean layout that helps users understand their financial situation at a glance

#### Alert System
* **Low Funds Warning System**:
  * Built a system that watches your available money and warns you when it gets too low
  * Created a flexible alert system that can be adjusted to different warning levels
  * Set up a default $5 warning threshold that notifies users before they run out of money
  * Used the Observer pattern to keep alerts separate from the core money tracking system

#### User Help System
* **Help Display**:
  * Created the `helpdisplay` class that explains how to use the app
  * Wrote clear instructions for all available commands
  * Organized help topics by category so users can quickly find what they need

### Project Management
* **GitHub Organization Setup**:
  * Created and configured the GitHub organization for the team
  * Set up the team repository with proper access controls
  * Established branch protection rules to enforce the forking workflow

* **Github Issues Management**
  * Created and standardized all issue tags/labels for better project tracking

* **Release Management**:
  * Setup milestones for v1.0, v2.0 & v3.0
  * Managed release v1.0 on GitHub

* **Community Contributions**:
  * Reviewed and approved Pull Requests with non-trivial review comments: [#35](https://github.com/AY2425S2-CS2113-T11A-4/tp/pull/35), [#24](https://github.com/AY2425S2-CS2113-T11A-4/tp/pull/24), [#45](https://github.com/AY2425S2-CS2113-T11A-4/tp/pull/45)

### Contributions to Team Tasks
* Wrote tests for the following classes:
  * Tests for the Summary module covering most possible scenarios
  * Tests for the alert system with simulated low funds situations
  * Tests for the display components

### Documentation

### User Guide:
* Established the overall structure and format of the User Guide to ensure consistency and readability
* Created standardized command format sections with clear syntax highlighting
* Added documentation for the Summary Display feature that shows financial information, [#48](https://github.com/AY2425S2-CS2113-T11A-4/tp/pull/48)
* Added documentation for the Help Display feature that lists all available commands
* Added documentation for the Low Funds Alert system that warns users when funds are low
* Added comprehensive error handling documentation for all financial operations

### Developer Guide:
* Created UML sequence diagrams to illustrate component interactions:
  * `Summary.puml`: Shows how the Summary class is connected to the rest of the program, [#48](https://github.com/AY2425S2-CS2113-T11A-4/tp/pull/48)
  * `ViewSummary.puml`: Shows how financial data is retrieved and displayed
  * `SetAlert.puml`: Illustrates the alert threshold configuration process
  * `TriggerAlert.puml`: Demonstrates the Observer pattern in action when funds are low
* Added implementation details of the Summary component including Observer pattern design
* Added implementation details of the SummaryDisplay component with sequence flows
* Added implementation details of the Funds Alert component with threshold management
* Added explanation of the income removal validation to prevent negative funds
