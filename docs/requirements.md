# Requirements

[<- Back to main](../README.md)

## Technological Platform

* The project must be developed using **JEE** (servlets, jspx, tomcat, MVC, etc.)
* Any **JavaScript** you use must be your own. You __may not__ use any library
  such as jQuery, Ext, or Dojo.
* You may use **images** or **CSS** developed by others with proper attribution.
  In that case, include an **acknowledgment** section in your report.

## The Business Model

The Foods R Us Company is a consolidating retailer. It specializes in selling
food items but it does not stock inventory. Instead, it takes purchase orders
(P/O's) from its clients, consolidates them, and then procures them from
business partners who stock the items and provide them wholesale; i.e. the
partners do not venture into the retail market. In this business model, Foods R
Us makes money by benefiting from volume discounts (due to consolidation) and by
marking up wholesale prices. The latter factor, however, can backfire: if the
markup is too high, the Company will not be competitive, and if it is too low,
it becomes vulnerable to market fluctuations and this may lead to a net loss (by
under-selling the suppliers). The Company has to deal with this risk and attempt
to mitigate it, hence the need for sophisticated eCommerce technologies.

## Overview of the System

The sought system consists of three parts:

1.  **B2C** enables the Company's (human) clients to browse its catalog, add items
    to a shopping cart, and place orders. This part culminates in several P/O
    files stored on disk.

2.  **B2B** is non-interactive and runs as a scheduled job with a frequency that
    depends on volume. Its input is the set of P/O files created by Part 1. It
    consolidates them by forming the union of the P/O's such that each item appears
    only once with a quantity equal to the sum of its quantities in all the P/O's.
    After creating the combined order, this part of the system connects to several
    web services and determines who supplies each item and at what price, optimizes
    the procurement, and places one or more order. This part is to be treated as a
    separate system because it runs asynchronously with B2C and possibly in a
    different box. Hence, some middleware system or process is needed to synch
    access to the P/O's.

3.  **AUTH** is an authentication server. When the B2C part needs to authenticate a
    client, it redirects traffic to AUTH to determine and confirm the client's
    identity. This part must also be treated as a totally separate system (likely
    running on a different box). Indeed, the company may one day outsource this
    server to a cloud provider (e.g. auhenticate via Facebook, Google, or Amazon)
    without affecting the other two parts. The loosely coupled nature of AUTH can
    also be used for making payments (e.g. simply use PayPal as AUTH).

## Analysis of Part 1 (B2C)

### Use Case #1: A client makes a fresh visit

The relevant URL is "host:port/eFoods". The Catalog servlet displays the catalog
of the Company. It is up to you to determine how this display is done based on
how much time you want to spend on this task and on how versatile you want your
webapp to be. For example, you can at one extreme display all the items in one
page; you can show the hierarchy by displaying only the categories and then the
selected items; you can enable both direct and category-based access; you can
have an express order form for those who know the item numbers; you can enable
clients to bookmark pages; etc. Whatever you do, the client must be able to see
the item's number, name, quantity per unit, and price. The client must also able
to add an item to an initially empty shopping cart.

### Use Case #2: A client adds an item to the shopping cart

The Cart servlet must react by displaying the content of the shopping cart. The
display should be tabular with one item per row. The table columns display the
number, name, and unit price of each item in a read-only fashion. In addition,
there should be a writable column for the desired quantity and a read-only one
for the extended price (defined as the quantity times the unit price). The
display should also indicate a total, a shipping cost ($5 that is waived for
orders of $100 or more before taxes), HST (13% of total, including shipping if
any), and a grand total. The page has three buttons: Update (to refresh the
calculated fields after editing a quantity), Continue Shopping, and Checkout.
Notice that if the entered quantity of an item is zero then it should be removed
from the cart. You need to also handle the case of negative or non-numeric
quantities.

### Use Case #3: A client logs in

Upon checkout (or optionally anytime before checkout at which the client chooses
to login), the request should be redirected to AUTH to determine and confirm the
client's identity. We assume that our clients have established accounts with the
AUTH server and, hence, their account numbers and account names can be retrieved
from AUTH upon a successful login.

### Use Case #4: A client checks out

Upon checkout, the controller must ensure the client is logged in and must then
display a confirmation screen followed by an acknowledgement that the the order
has been accepted and is being processed. A URL that the client can visit at any
time to view the created P/O must also be provided. See the next use case for
details.

### Use Case #5: A client visits the URL of a P/O

Upon confirmation of a P/O, the system stores its content in an XML file based
on the PO.xsd schema. The name of the P/O file is derived from the account
number of the client and the P/O number (a per-client serial number that starts
at 1). For example, the the 3rd P/O of account number 12345, is: po12345_03.xml.
Since this is an XML file, it needs to be transformed to XHTML before the client
can see it. Note that this use case does not involve authentication.

In addition to the above use cases, it is recommended that your site supports
the following uses: the ability to view the shopping cart from the catalog
screen (i.e. without having to add an item); the ability to checkout from the
catalog screen.

### Analytics and Special Features

Add support for the following:

* Management wants to be able to determine the average time it takes a client to
  add an item to the cart and the average time between a fresh visit and checkout
  (in the same session). Provide a mechanism by which these two averages can be
  viewed in real time.
* And on an ad-hoc basis, Management may decide to advertise a certain item
  whenever a related item is added to the cart. As a proof of concept, activate
  this by cross selling 2002H712 whenever 1409S413 is added to the cart. Keep in
  mind that this functionality should not be part of the webapp since Management
  may withdraw it at any time (w/o recompiling).
* Add a search facility to enable clients to look for items.

## Analysis of Part 2 (B2B

This off-line, non-interactive system runs asynchronously with the web server.
It consolidates all the P/Os of the day to generate a procurement order: a list
of all the needed items and the overall quantity needed for each. Next, it
connects to the web services of three major Canadian wholesalers, one in
Toronto, one in Vancouver, and one in Halifax. For each procurement item, it
determines who has it, chooses the one with the lowest price, and places an
order. No shipping charges or taxes are in scope here. When procurement is
completed, it generates a procurement report (HTML) detailing the ordered items,
the chosen wholesaler of each, and the winning bid price. Placing an order
requires a key. A unique key will be given in class.

## Analysis of Part 3 (AUTH)

This server receives redirected requests from the B2C server. When such a
request is received, AUTH must allow the client to type in username and
password. For this project, AUTH must be implemented so that it only accepts CSE
credentials; i.e. from those who have accounts on our \@cse.yorku.ca system. If
login is succesful, AUTH must redirect back to B2C and provide the CSE username
(i.e. the login name) as account number and the CSE full name as account name.
The design and implementation of AUTH must keep security in mind: All
vulnerabilities and potential exploits must be identified in the report and the
logic must be hardened to gaurd against as many of these threats as possible.

## Specifications

* Use the two tables: **Category** and **Item** in the "CSE" database. Note
  that you may not use any other table or database and that you must treat
  these two tables as dynamic (can change from day to day) and read-only (you
  cannot write to them).
* The purchase order [schema](PO.xsd). Here is a [sample](PO.xml) that adheres
  to the schema.

The following files describe the web services from which the company procures
its products:

* The [WSDL of the Toronto](YYZ.wsdl) Wholesaler.
* The [WSDL of the Vancouver](YVR.wsdl) Wholesaler.
* The [WSDL of the Halifax](YHZ.wsdl) Wholesaler.

## Sample Project

The home page has a link to a project that is similar to, but does not meet all
the requirements, the sought peoject. For example, it does not have an AUTH part
and its data source is not exactly the same. Nevertheless, it gives you an idea
about the sought functionality.

## Deliverables

There are two deliverables:

* A Project **Report** (described below)
* A **war** file (make sure it includes source files and Group.txt)

These need to be submitted (one by one) before the date of the group's chosen
Project-C slot. Use either [Web Submit](https://webapp.cse.yorku.ca/submit/)
with assignment projC or issue the submit command:

```
  submit 4413 projC <file>
```

where `file` is the report file or the project's war file.

## The Report

The report is meant to describe your execution of the project to someone who is
familiar with the project, e.g. a fellow student who is also executing the same
project and writing a similar report but in a different team. Hence, the report
should focus on your own experience and efforts, i.e. what you actually did,
rather than on general description. It is expected to be very brief, to the
point, and made up of at most ten pages of typed text and diagrams (excluding
output samples and source listing).

The report should be packaged as one document (pdf or docx); should have page
numbers; and should have a table of contents (TOC) in which all sections after
the front material and listed along with their corresponding page numbers. The
report consists of the following parts:

1.  **Front Matter**: A cover page (course number and name, date, project name, team);
    an optional dedication page; the TOC

2.  **Design**: Describe your system's architecture and data flow briefly (diagrams are
    ideal here). Also, several design issues and decisions must have popped up
    during the analysis and design phases, e.g. MVC, namespaces, file formats,
    algorithms, representations, connecting B2B with B2C, etc. Describe in this
    sections the key issues and decisions that were made and why they were made the
    way they did.

3.  **Implementation**: This is similar to the previous part but pertains to
    implementation issues and decisions. This section should also include Testing
    and Status. The former discusses how the project was tested and the latter lists
    each and every limitation or shortcoming of the finished webapp with respect to
    requirement.

4.  **The Team**: It is expected that every team member fully masters each and every
    aspect of the submitted project, regardless of who actually implemented it, and
    hence, should be able to not only explain it but also make changes to it. As
    such this section starts with a paragraph that describes how the work was
    divided amongst the team members; how did the team members arranged their
    meetings and how often; and how was consistency maintained. The next paragraph
    is about lessons learned; i.e. what would you do differently were you to start
    over. Next, there is one paragraph per team member. Each such paragraph starts
    with the member's name and the role played. It then explains in detail the scope
    of the member's contribution. It then explains how the member learned about
    elements of the project that were done by others. Finally, it asserts if the
    member fully understands all aspects of the work and can make changes to it in a
    test environment or if there are certain aspects that are not fully mastered.
    The last part of this section should contain the paragraph: I hereby attest to
    the accuracy of the information contained in "The Team" section of this Report.
    followed by the name and signature of every member of the team.

5.  **Output Samples**: These samples are for the B2B part. Select any XML P/O and
    include a listing of how it appears on disk and a screenshot of how it appears
    in the browser. Include also one completed procurement report showing: the items
    to procure, the consolidated quantity per item, the chosen wholesaler and price
    per item, and the confirmation codes obtained from the wholesalers web services.

6.  **The Source Code**: Provide listing of all programs (including jspx, tags, xsl,
    web.xml, etc.). Partition this section into subsections, provide a title for
    each, and add an entry (with a page number) for each such subsection in the TOC.
    Make sure the code is properly formatted. Some editors do not format properly
    because they use spaces instead of tabs and/or use proportional fonts. Do not
    delay this step to the last minute; resolve it early on.

## Evaluation

The project will be evaluated based (50%) on meeting the specs (both system
functionality and report contents) and (30%) on internal quality (design, style,
code, etc.). The remaining (20%) is based on how your project compares with
those of other teams.
