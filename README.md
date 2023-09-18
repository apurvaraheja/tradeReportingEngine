# Design and Implementation of an XML Event Processing System - Trade Reporting System with Spring Boot

## Introduction

This project presents the design and implementation of a robust XML event processing system using Spring Boot, 
aimed at efficiently extracting, storing, and filtering event data from XML files. This system allows for flexibility and scalability,
enabling easy adaptation to evolving business needs.

### Problem Statement

The primary challenge addressed by this project is the need to process XML event files efficiently, extract relevant elements, 
store the data in a database, and apply specific filtering criteria to obtain meaningful insights from the events. 
Additionally, the system must provide a well-defined RESTful API for ease of interaction with external applications.

### Solution Overview

The proposed solution leverages Spring Boot, a powerful framework for building Java-based applications. The system comprises several key components:

1. **XML Parsing and Storage:** We utilize Java's built-in XML parsing capabilities, including `javax.xml.parsers.DocumentBuilder` and `javax.xml.xpath.XPath`, to efficiently extract elements from XML files. The extracted data is then stored in an embedded database (H2, in this case) using Spring Data JPA.

2. **Data Model:** An `Event` entity class defines the structure of the stored event data. This model is adaptable, allowing for future extensions or additional fields without affecting existing functionality.

3. **Filtering and Criteria:** The heart of the system lies in its ability to filter events based on specified criteria. This includes ensuring that the `seller_party` is either 'EMU_BANK' with `premium_currency` 'AUD' or 'BISON_BANK' with `premium_currency` 'USD'. Furthermore, the system verifies that `seller_party` and `buyer_party` are not anagrams, a task accomplished using Apache Commons Lang.

4. **RESTful API:** The system exposes a RESTful API with two main endpoints. The `/events/upload` endpoint allows users to upload XML files for processing, while the `/events/filtered-events` endpoint returns a JSON response containing the filtered event data that meets the criteria. API keys are enforced to ensure security and rate limiting, with the rate limit set at five requests per hour per key.

### Scalability and Maintainability

This design emphasizes scalability and maintainability. By utilizing Spring Boot's modularity and the adaptability of the data model, the system can easily accommodate changes in filtering criteria or the addition of new fields in future iterations. Scalability is achieved through the use of Spring's dependency injection and a well-structured design, making it feasible to handle larger datasets and increased traffic with minimal code adjustments.

## Conclusion

The XML event processing system presented in this project offers an effective and adaptable solution for extracting, storing, and filtering event data from XML files. By leveraging Spring Boot and established Java libraries, we've created a robust and maintainable system that meets current requirements while remaining flexible enough to evolve with changing business needs.
