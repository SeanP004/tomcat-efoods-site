#  B2B: Offline Procurement Application

[<- Back to main](../README.md)

* [Getting Started](#start)
* [Usage](#usage)
* [Configurations](#config)
* [Scheduler](#sched)
* [Structure](#struct)

### NOTE: This application needs to connect with a third-party web service that appears to be no longer available. Thus, this application <span style="text-decoration: underline">WILL NOT WORK CORRECTLY</span>.

## <a name="start"></a>Getting Started

1.  Run the following commands in the terminal to start the
    virtual machine and to connect into the server. See the
    main [README](../README.md) for more details.

        vagrant up
        vagrant ssh

2.  Update the config file as necessary, see [Configurations](#config) section.
3.  To recompile the source code, run the following command:

        b2b_manage compile

## <a name="usage"></a>Usage

1.  To execute, run the following command:

        b2b_manage run

2.  Console will display a message once the application is
    finished generating the requested files. The following message will
    be generated as shown below:

        ------ HTML file has be Generated --------

    when complete, or:

        No Orders

    when it can not find orders.
3.  The generated reports can be found in the ./reports directory
    inside where B2B is stored. To view the generated reports, opened
    the reports in a web browser.

## <a name="config"></a>Configurations

### `./config.ini`:

Parameters   | Description
------------ | ------------------------------
`storeDir`   | The directory where the generated report files are located and stored in.
`dataURL`    | The URL directory location where the eFoods reports are stored for extracting data.
`WTORONTO`   | The URL to where the WSDL file for WTORONTO
`WVANCOUVER` | The URL to where the WSDL file for WVANCOUVER
`WHALIFAX`   | The URL to where the WSDL file for WHALIFAX
`key`        | Agreed secret key with authenticate service
`xslt`       | The URL to xml template to format the files output

## <a name="sched"></a>Scheduler

Below is a brief instruction for setting up a scheduled task.

1.  To install or create or edit cron jobs:

        crontab -e

        1 2 3 4 5 /home/vagrant/bin/b2b_manage run

    Where:

    * 1: Minute (0-59)
    * 2: Hours (0-23)
    * 3: Day (0-31)
    * 4: Month (0-12 [12 == December])
    * 5: Day of the week(0-7 [7 or 0 == sunday])

## <a name="struct"></a>Structure

    B2B                     # Root directory
    ├── res                 # Output assets
    │   ├── css             # Stylesheet
    │   └── xmlres          # XML rendering assets
    └── src                 # Java source code
        ├── controller      # Main app startup class
        └── model           # Business logic (model)
            ├── common      # Shared utilities
            ├── exception   # Exception handlers
            ├── order       # Order data structure
            └── xml         # XML generators and handlers

[<- Top of Page](#top)
