# Touch Sensitive Form Module for OpenMRS (openmrs-module-nuform)

![OpenMRS NuForm Module](https://raw.github.com/dermatologist/openmrs-module-nuform/master/docs/nuform.png)

[![Build Status](https://travis-ci.org/dermatologist/openmrs-module-nuform.svg)](https://travis-ci.org/dermatologist/openmrs-module-nuform)

## What is openMRS?

openMRS is a highly customizable EMR platform that can be extended and customized by user-contributed modules. 

More details here: [http://openmrs.org](http://openmrs.org)

## What is openmrs-module-nuform

My physician colleague once told me that he would use an EMR only if he can write on it like plain paper. Here it is! A onenote like experience for forms, as close to paper as it can get.

Instructions to Use:

1. Click on NuForm Icon on the dashboard.
2. Upload your form image and click the 'down' arrow to transfer the name of the image below. You can also use an online image URL here.
3. Create a NuForm definition. A general NuForm is patient independent while patient specific NuForms can be edited from the patient dashboard. A research data collection form might be general while a surgical checklist is patient specific.
4. Fill/Write Nuforms by clicking on the Create Icon. General NuForms can be filled from the dashboard while patient specific ones can be filled from the patient dashboard.

Annotation:

If you have installed my 'Clinical Images' module, you can annotate your clinical images using nuform. A typical use case would be marking malignant cells on a histopathology slide image. Click on the 'Annotate' tab to create a patient-specific form with the image.

Development:

The inking engine is an [angular2 project on GitHub using FabricJS](https://github.com/E-Health/angular2-nuform).
 The NuForm is also available as an [eForm for OSCAR EMR](http://nuchange.ca/2016/06/touch-sensitive-oscar-eform.html).

## How do I install and use this module?
- [Download latest release](https://modules.openmrs.org/#/show/256/nuform). Upload omod to OpenMRS.
- Watch this video: [http://nuchange.ca/openmrs-module-nuform-video](http://nuchange.ca/openmrs-module-nuform-video)

## Credits
* fabricJS
* Angular2

## Contact Me
* Visit [my blog](http://nuchange.ca).
* [Issues and Feature Requests](https://github.com/dermatologist/openmrs-module-nuform/issues)
