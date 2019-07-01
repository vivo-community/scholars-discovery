# GraphQL

query:
```
{
  personExists(id: "nad61fbd7")
}
```
<details> <summary>response:</summary>

{
  "data": {
    "personExists": true
  }
}

</details>

query:
```
{
  personCount(
    query: "*",
    fields: [
      "positions.organizations.parent"
    ],
    params: {
      positions_organizations_parent_filter: [
          "College of Liberal Arts"
      ]
    }
  )
}
```
<details> <summary>response:</summary>

{
  "data": {
    "personCount": 338
  }
}

</details>

query:
```
{
  person(id: "nf489b17d") {
    id,
    name,
    positions {
      id,
      label,
      type
      organizations {
        id,
        label,
        parent {
          id,
          label
        }
      }
    },
    educationAndTraining {
      organization {
        id,
        label
      },
      field,
      abbreviation,
      endDate
    },
    researchAreas {
      label
    },
    publications {
      type,
      publisher,
      date,
      venue {
        id,
        label,
        type
      }
    }
  }
}
```
<details> <summary>response:</summary>

{
  "data": {
    "person": {
      "id": "nf489b17d",
      "name": "Herbert, Bruce",
      "positions": [
        {
          "id": "n34e4ddc0",
          "label": "Professor",
          "type": "FacultyPosition",
          "organizations": [
            {
              "id": "nc82ffe80",
              "label": "Geology and Geophysics",
              "parent": [
                {
                  "id": "nfeec0e89",
                  "label": "College of Geosciences"
                }
              ]
            }
          ]
        },
        {
          "id": "n1dc20ddd",
          "label": "Director, Office of Scholarly Communications and Professor",
          "type": "FacultyPosition",
          "organizations": [
            {
              "id": "nb7f16f5c",
              "label": "University Libraries",
              "parent": [
                {
                  "id": "n69864197",
                  "label": "Texas A&M University Corpus Christi"
                },
                {
                  "id": "n69864197",
                  "label": "Texas A&M University Texarkana"
                },
                {
                  "id": "n69864197",
                  "label": "Texas A&M University Commerce"
                },
                {
                  "id": "n69864197",
                  "label": "Texas A&M University at Galveston"
                },
                {
                  "id": "n69864197",
                  "label": "Texas A&M University at Qatar"
                },
                {
                  "id": "n69864197",
                  "label": "Texas A&M University - College Station, Texas, United States"
                },
                {
                  "id": "n69864197",
                  "label": "Texas A&M University"
                }
              ]
            }
          ]
        }
      ],
      "educationAndTraining": [
        {
          "organization": {
            "id": "nfb6e730e",
            "label": "Colgate University"
          },
          "field": "Chemistry",
          "abbreviation": "B.A.",
          "endDate": "1982-01-01T00:00:00"
        },
        {
          "organization": {
            "id": "n0734e5b8",
            "label": "University of California, Riverside"
          },
          "field": "Soil Science",
          "abbreviation": "M.S.",
          "endDate": "1988-01-01T00:00:00"
        },
        {
          "organization": {
            "id": "n0734e5b8",
            "label": "University of California, Riverside"
          },
          "field": "Soil Science",
          "abbreviation": "Ph.D.",
          "endDate": "1992-01-01T00:00:00"
        }
      ],
      "researchAreas": [
        {
          "label": "Research--Management"
        },
        {
          "label": "Soils--Quality"
        },
        {
          "label": "Communication in learning and scholarship"
        },
        {
          "label": "Open access"
        },
        {
          "label": "Academic-industrial collaboration"
        }
      ],
      "publications": [
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2003-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS National Meeting Book of Abstracts",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "American Geophysical Union (AGU)",
          "date": "2006-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2006-01-01T00:00:00",
          "venue": {
            "id": "n0072-1077ISSN",
            "label": "ISOTOPIC AND ELEMENTAL TRACERS OF CENOZOIC CLIMATE CHANGE",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1995-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS National Meeting Book of Abstracts",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2014-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2005-01-01T00:00:00",
          "venue": {
            "id": "n0361-5995ISSN",
            "label": "Soil Science Society of America Journal",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n1010-6030ISSN",
            "label": "Journal of Photochemistry and Photobiology A: Chemistry",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1996-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ABSTRACTS OF PAPERS OF THE AMERICAN CHEMICAL SOCIETY",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2005-01-01T00:00:00",
          "venue": {
            "id": "n0943-0105ISSN",
            "label": "Environmental Geology",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2012-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "Environmental science & technology",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n0043-1354ISSN",
            "label": "Water Research",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0093-3066ISSN",
            "label": "ACS Division of Environmental Chemistry, Preprints",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Springer Nature",
          "date": "2009-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "AIChE Annual Meeting, Conference Proceedings",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "1999-01-01T00:00:00",
          "venue": {
            "id": "n0730-7268ISSN",
            "label": "Environmental Toxicology and Chemistry",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "American Chemical Society (ACS)",
          "date": "1987-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": "Sage Publications",
          "date": "2005-01-01T00:00:00",
          "venue": {
            "id": "n0361-1981ISSN",
            "label": "ISSUES IN PAVEMENT DESIGN AND REHABILITATION",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "1996-01-01T00:00:00",
          "venue": {
            "id": "n0047-2425ISSN",
            "label": "Journal of Environmental Quality",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2009-01-01T00:00:00",
          "venue": {
            "id": "n0361-5995ISSN",
            "label": "Soil Science Society of America Journal",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2002-01-01T00:00:00",
          "venue": {
            "id": "n0043-1397ISSN",
            "label": "Water Resources Research",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2002-01-01T00:00:00",
          "venue": {
            "id": "n0043-1397ISSN",
            "label": "Water Resources Research",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": {
            "id": "n1089-9995ISSN",
            "label": "Journal of Geoscience Education",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": "American Geophysical Union (AGU)",
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n2169-9313ISSN",
            "label": "Journal of Geophysical Research B: Solid Earth",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1993-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ABSTRACTS OF PAPERS OF THE AMERICAN CHEMICAL SOCIETY",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2012-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "ENVIRONMENTAL SCIENCE & TECHNOLOGY",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "Environmental Science & Technology",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "Environmental Science & Technology",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "Environmental Science & Technology",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": "American Society of Civil Engineers (ASCE)",
          "date": "2010-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2010-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ABSTRACTS OF PAPERS OF THE AMERICAN CHEMICAL SOCIETY",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "ASTM International",
          "date": "2007-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2003-01-01T00:00:00",
          "venue": {
            "id": "n0361-5995ISSN",
            "label": "Soil Science Society of America Journal",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2010-01-01T00:00:00",
          "venue": {
            "id": "n1420-2026ISSN",
            "label": "Environmental Modeling and Assessment",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS National Meeting Book of Abstracts",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Elsevier bv",
          "date": "2011-01-01T00:00:00",
          "venue": {
            "id": "n0269-7491ISSN",
            "label": "Environmental pollution (Barking, Essex : 1987)",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2016-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2009-01-01T00:00:00",
          "venue": {
            "id": "n0361-5995ISSN",
            "label": "Soil Science Society of America Journal",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1993-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "AIChE Annual Meeting, Conference Proceedings",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2008-01-01T00:00:00",
          "venue": {
            "id": "n0146-6380ISSN",
            "label": "Organic Geochemistry",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS Photonics",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2010-01-01T00:00:00",
          "venue": {
            "id": "n0309-8265ISSN",
            "label": "JOURNAL OF GEOGRAPHY IN HIGHER EDUCATION",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2011-01-01T00:00:00",
          "venue": {
            "id": "n0045-6535ISSN",
            "label": "Chemosphere",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0093-3066ISSN",
            "label": "ACS Division of Environmental Chemistry, Preprints",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "1997-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2008-01-01T00:00:00",
          "venue": {
            "id": "n0146-6380ISSN",
            "label": "Organic Geochemistry",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "1993-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "Environmental Science &amp; Technology",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS Photonics",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "1997-01-01T00:00:00",
          "venue": {
            "id": "n1078-7275ISSN",
            "label": "Environmental & Engineering Geoscience",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ABSTRACTS OF PAPERS OF THE AMERICAN CHEMICAL SOCIETY",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n0047-2425ISSN",
            "label": "Journal of Environmental Quality",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "GeoScienceWorld",
          "date": "1997-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0093-3066ISSN",
            "label": "ACS Division of Environmental Chemistry, Preprints",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2001-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS Photonics",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2009-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": "American Geophysical Union (AGU)",
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n2169-9313ISSN",
            "label": "Journal of Geophysical Research B: Solid Earth",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2009-01-01T00:00:00",
          "venue": {
            "id": "n1089-9995ISSN",
            "label": "Journal of Geoscience Education",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Elsevier bv",
          "date": "2004-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS National Meeting Book of Abstracts",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2003-01-01T00:00:00",
          "venue": {
            "id": "n0047-2425ISSN",
            "label": "Journal of Environmental Quality",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2011-01-01T00:00:00",
          "venue": {
            "id": "n0013-936XISSN",
            "label": "Environ Sci Technol",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Sage Publications",
          "date": "2012-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2012-01-01T00:00:00",
          "venue": {
            "id": "n1059-0145ISSN",
            "label": "Journal of Science Education and Technology",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Association for the Advancement of Computing in Education (AACE)",
          "date": "2007-01-01T00:00:00",
          "venue": {
            "id": "n1528-5804ISSN",
            "label": "Contemporary Issues in Technology and Teacher Education",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": {
            "id": "n2153-5965ISSN",
            "label": "Annual ASEE Conference Proceedings, Louisville, Kentucky June 20-23, 2010",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n1010-6030ISSN",
            "label": "Journal of Photochemistry and Photobiology A: Chemistry",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": {
            "id": "n2573-5438ISSN",
            "label": "JOURNAL OF TRANSPORTATION ENGINEERING PART B-PAVEMENTS",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2016-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2010-01-01T00:00:00",
          "venue": {
            "id": "n0040-6031ISSN",
            "label": "Thermochimica Acta",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1993-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "AIChE Annual Meeting, Conference Proceedings",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Springer Nature",
          "date": "2012-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "1988-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2002-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ACS National Meeting Book of Abstracts",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0093-3066ISSN",
            "label": "ACS Division of Environmental Chemistry, Preprints",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "AIChE Annual Meeting, Conference Proceedings",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2014-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2014-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1996-01-01T00:00:00",
          "venue": {
            "id": "n0093-3066ISSN",
            "label": "ACS Division of Environmental Chemistry, Preprints",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2018-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "Chapter",
          "publisher": "ASCE Publications",
          "date": "2009-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2008-01-01T00:00:00",
          "venue": {
            "id": "n1089-9995ISSN",
            "label": "Journal of Geoscience Education",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1996-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2017-01-01T00:00:00",
          "venue": {
            "id": "n1089-9995ISSN",
            "label": "Journal of Geoscience Education",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2003-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "AIChE Annual Meeting, Conference Proceedings",
            "type": "Journal"
          }
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2001-01-01T00:00:00",
          "venue": {
            "id": "n0093-3066ISSN",
            "label": "ACS Division of Environmental Chemistry, Preprints",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": null,
          "date": "2001-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "ABSTRACTS OF PAPERS OF THE AMERICAN CHEMICAL SOCIETY",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": "American Society of Civil Engineers",
          "date": "2000-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2016-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2000-01-01T00:00:00",
          "venue": {
            "id": "n0895-0563ISSN",
            "label": "PANAM UNSATURATED SOILS 2017: APPLICATIONS",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1997-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "Chapter",
          "publisher": "Soil Science Society of America",
          "date": "1995-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "GreyLiterature",
          "publisher": null,
          "date": "2015-01-01T00:00:00",
          "venue": null
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n0016-7037ISSN",
            "label": "GEOCHIMICA ET COSMOCHIMICA ACTA",
            "type": "Journal"
          }
        },
        {
          "type": "ConferencePaper",
          "publisher": null,
          "date": "1997-01-01T00:00:00",
          "venue": {
            "id": "n0065-7727ISSN",
            "label": "AIChE Annual Meeting, Conference Proceedings",
            "type": "Journal"
          }
        },
        {
          "type": "AcademicArticle",
          "publisher": "Crop Science Society of America",
          "date": "2004-01-01T00:00:00",
          "venue": {
            "id": "n0047-2425ISSN",
            "label": "Journal of Environmental Quality",
            "type": "Journal"
          }
        }
      ]
    }
  }
}

</details>

query:
```
{
  organizations(
    query: "*",
    index: "name,STARTS_WITH,T",
    facets: [
      "type"
    ],
    params: {},
    paging: {
      pageNumber: 0,
      pageSize: 100,
      sort: {
        orders: [{
          property: "name",
          direction: "ASC"
        }]
      }
    }
  ) {
      id,
    name
  }
}
```
<details> <summary>response:</summary>

{
  "data": {
    "organizations": [
      {
        "id": "n54e5b565",
        "name": "T l com ParisTech"
      },
      {
        "id": "n7b993e9d",
        "name": "Taiwan Association for Aerosol Research"
      },
      {
        "id": "nab71cf72",
        "name": "Tamesis Books"
      },
      {
        "id": "ndd0186c5",
        "name": "Tamesis Books Ltd"
      },
      {
        "id": "ndb52daa8",
        "name": "Tamil Nadu Veterinary and Animal Sciences University"
      },
      {
        "id": "nde476d20",
        "name": "Tamkang University"
      },
      {
        "id": "nddebfe6c",
        "name": "Tamkang University Press"
      },
      {
        "id": "ne6388bf7",
        "name": "TAMU Press"
      },
      {
        "id": "nf28b32ae",
        "name": "Tanta University"
      },
      {
        "id": "n2d9191df",
        "name": "Taras Shevchenko National University of Kyiv"
      },
      {
        "id": "n65309ef5",
        "name": "Tarbiat Modares University"
      },
      {
        "id": "n1a569c76",
        "name": "Tarleton State University"
      },
      {
        "id": "naeb62dcb",
        "name": "Tarrant County College"
      },
      {
        "id": "n2c8838b4",
        "name": "Tashkent Institute of Chemical Technology"
      },
      {
        "id": "n2989eb12",
        "name": "Taylor %26 Francis (Routledge): SSH Titles"
      },
      {
        "id": "n157a64e9",
        "name": "Taylor & Francis"
      },
      {
        "id": "n4667ab23",
        "name": "Taylor & Francis (Routledge)"
      },
      {
        "id": "n69f8fa64",
        "name": "Taylor & Francis Books"
      },
      {
        "id": "na803057d",
        "name": "Taylor & Francis Group"
      },
      {
        "id": "nab2fdd98",
        "name": "Taylor & Francis, Ltd."
      },
      {
        "id": "n16f721ef",
        "name": "Taylor &amp; Francis"
      },
      {
        "id": "n9f5f0f95",
        "name": "Taylor &amp; Francis Books"
      },
      {
        "id": "n321441d1",
        "name": "Taylor &amp; Francis, Ltd."
      },
      {
        "id": "nc674dbb2",
        "name": "Taylor &amp; Francis: STM, Behavioural Science and Public Health Titles"
      },
      {
        "id": "n3d483021",
        "name": "Taylor and Francis"
      },
      {
        "id": "n6e225061",
        "name": "Taylor and Francis Online"
      },
      {
        "id": "n09247f67",
        "name": "Teachers College Press"
      },
      {
        "id": "na4804b43",
        "name": "TEACHING Exceptional Children"
      },
      {
        "id": "n833d3ed2",
        "name": "Teaching, Learning and Culture"
      },
      {
        "id": "ndbe46305",
        "name": "Teaduste Akadeemia"
      },
      {
        "id": "n666f0c63",
        "name": "Technical Centre for Agricultural and Rural Cooperation"
      },
      {
        "id": "nc7dbedd6",
        "name": "Technical University Munich"
      },
      {
        "id": "n54328b40",
        "name": "Technical University of Berlin"
      },
      {
        "id": "n2e0ae5d3",
        "name": "Technical University of Darmstadt"
      },
      {
        "id": "n37fc4fb3",
        "name": "Technical University of Denmark"
      },
      {
        "id": "n9cbbbc00",
        "name": "Technical University of Madrid"
      },
      {
        "id": "n344dab2c",
        "name": "Technical University of Sofia"
      },
      {
        "id": "n84e0c95d",
        "name": "Technion   Israel Institute of Technology"
      },
      {
        "id": "n12eb8666",
        "name": "Technological Educational Institute of Epirus"
      },
      {
        "id": "n342416dc",
        "name": "Technology Review"
      },
      {
        "id": "n80f5bc68",
        "name": "Tehran University of Art"
      },
      {
        "id": "n14e51c66",
        "name": "Tel Aviv University"
      },
      {
        "id": "n3e6f5014",
        "name": "Temple Univ Pr"
      },
      {
        "id": "n37999d90",
        "name": "Temple University"
      },
      {
        "id": "n50aade72",
        "name": "Temple University Press"
      },
      {
        "id": "n390f39d0",
        "name": "Temporary Publisher"
      },
      {
        "id": "n43d7819e",
        "name": "Tempus Publications"
      },
      {
        "id": "nf8e380c3",
        "name": "Tennessee State University"
      },
      {
        "id": "n371e3148",
        "name": "Tennessee Technological University"
      },
      {
        "id": "n499c6835",
        "name": "Test accounts"
      },
      {
        "id": "n21498",
        "name": "Test Conferrred By Conferrer"
      },
      {
        "id": "n5578",
        "name": "Test Organization Name"
      },
      {
        "id": "n507",
        "name": "Test Outreach & Community Service Organization Name"
      },
      {
        "id": "n6960",
        "name": "Test Oxford University Press Organization Name"
      },
      {
        "id": "ne5b62010",
        "name": "Teton NewMedia"
      },
      {
        "id": "n2d9e0590",
        "name": "Texas A %26 M University Press"
      },
      {
        "id": "necba0f65",
        "name": "Texas A & M Univ Pr"
      },
      {
        "id": "n2aab54ec",
        "name": "Texas A & M University"
      },
      {
        "id": "n26a8ab09",
        "name": "Texas A &amp; M University"
      },
      {
        "id": "n1da24196",
        "name": "Texas A &amp; M University Press"
      },
      {
        "id": "n2d1ce828",
        "name": "Texas A%26M Health Science Center School of Public Health, Southwest Rural Health Research Center"
      },
      {
        "id": "n8761aeed",
        "name": "Texas A&amp;M Health Science Center School of Public Health, Southwest Rural Health Research Center"
      },
      {
        "id": "n3973dfc9",
        "name": "Texas A&amp;M University Press"
      },
      {
        "id": "n2e7d2678",
        "name": "Texas A&amp;M University, Department of Economics"
      },
      {
        "id": "na57124aa",
        "name": "Texas A&M AgriLIFE Extension"
      },
      {
        "id": "nc8b9f721",
        "name": "Texas A&M AgriLife Research"
      },
      {
        "id": "n4a32e2e0",
        "name": "Texas A&M at Qatar"
      },
      {
        "id": "n54282dd0",
        "name": "Texas A&M Clinical Science and Translational Research Institute"
      },
      {
        "id": "n952c84ff",
        "name": "Texas A&M Engineering Experiment Station (TEES)"
      },
      {
        "id": "ngrid.418866.5",
        "name": "Texas A&M Health Science Center"
      },
      {
        "id": "n49897dc9",
        "name": "Texas A&M Health Science Center"
      },
      {
        "id": "ngrid.412408.b",
        "name": "Texas A&M Health Science Center (College Station)"
      },
      {
        "id": "n086c59eb",
        "name": "Texas A&M Health Science Center (College Station)"
      },
      {
        "id": "n91fdc864",
        "name": "Texas A&M Health Science Center (Temple)"
      },
      {
        "id": "n964fa77c",
        "name": "Texas A&M Institute of Data Science"
      },
      {
        "id": "n39556b98",
        "name": "Texas A&M International University"
      },
      {
        "id": "ne80f7afc",
        "name": "Texas A&M Law Scholarship"
      },
      {
        "id": "nb8d54ef0",
        "name": "Texas A&M School of Law"
      },
      {
        "id": "n364ccf55",
        "name": "Texas A&M Transportation Institute (TTI)"
      },
      {
        "id": "ngrid.264756.4",
        "name": "Texas A&M University"
      },
      {
        "id": "nc712f734",
        "name": "Texas A&M University at Galveston"
      },
      {
        "id": "nbe7046f0",
        "name": "Texas A&M University at Galveston"
      },
      {
        "id": "nc01bb64d",
        "name": "Texas A&M University at Qatar"
      },
      {
        "id": "n4b0e4d2b",
        "name": "Texas A&M University at Qatar"
      },
      {
        "id": "ned9805ff",
        "name": "Texas A&M University at Qatar"
      },
      {
        "id": "n2fc905f2",
        "name": "Texas A&M University Commerce"
      },
      {
        "id": "n69864197",
        "name": "Texas A&M University Corpus Christi"
      },
      {
        "id": "n71439efe",
        "name": "Texas A&M University Corpus Christi"
      },
      {
        "id": "ne72664d2",
        "name": "Texas A&M University Libra"
      },
      {
        "id": "n6b9c28dc",
        "name": "Texas A&M University Libraries"
      },
      {
        "id": "n4265715d",
        "name": "Texas A&M University Press"
      },
      {
        "id": "ngrid.264763.2",
        "name": "Texas A&M University System"
      },
      {
        "id": "n2e670aeb",
        "name": "Texas A&M University System"
      },
      {
        "id": "na9fd57ea",
        "name": "Texas A&M University-Central Texas"
      },
      {
        "id": "n7eb420e4",
        "name": "Texas A&M University-Kingsville"
      },
      {
        "id": "nbc6e1b2b",
        "name": "Texas A&M Veterinary Medicine Diagnostic Laboratory"
      },
      {
        "id": "nde1275fa",
        "name": "Texas Christian University"
      },
      {
        "id": "n500798d8",
        "name": "Texas Conference on Digital Libraries"
      },
      {
        "id": "n7769dc17",
        "name": "Texas Dental Association"
      },
      {
        "id": "ngrid.438736.d",
        "name": "Texas Higher Education Coordinating Board"
      }
    ]
  }
}

</details>

query:
```
{
  documents(paging: {
    pageNumber: 0,
    pageSize: 10,
    sort: {
      orders: [{
        property: "title",
        direction: "ASC"
      }]
    }
  }) {
    id,
    doi,
    title,
    authors {
            id,
      label,
      organizations {
                id,
        label
        }
    },
    abstractText
  }
}
```
<details> <summary>response:</summary>

{
  "data": {
    "documents": [
      {
        "id": "n92802SE",
        "doi": "10.2307/2901315",
        "title": " [B]igger's Place\\\": Lynching and Specularity in Richard Wright's \\\"Fire and Cloud\\\" and Native Son",
        "authors": [
          {
            "id": "n933887a0",
            "label": "Tuhkanen, Mikko",
            "organizations": [
              {
                "id": "nbc3b05cc",
                "label": "English"
              }
            ]
          }
        ],
        "abstractText": "Focuses on the novel \\\"Native Son\\\" and the short story \\\"Fire and Cloud,\\\" by Richard Wright. Differences and similarities of the two literary works; Lynching and enforced visibility; Characteristics of Wright's writings."
      },
      {
        "id": "n363605SE",
        "doi": null,
        "title": "#SayHerName Captured: Using Video to Challenge Law Enforcement Violence Against Women",
        "authors": [
          {
            "id": "n8407f47f",
            "label": "Baylor, Amber",
            "organizations": null
          }
        ],
        "abstractText": "Kianga Mweba’s cellphone camera blurs into darkness broken by flashes of lights surrounding her car. From the audio of her cellphone recording, one can hear Kianga Mweba scream as she is pulled out of the car and tased. Mweba, arrested as she filmed the police detaining a man on the street, was charged with attempted assault on an officer. After recovering footage from her phone, her defense attorney produced the video as evidence against the criminal charges. Now the recording is a key piece of evidence in a lawsuit against the department. Mweba’s experience, captured by her cellphone camera, rallied activists in Baltimore and across the country in demanding change to policing practices. This recording of police officers violently taking down Mweba was one of many videos released this year showing people of color violently seized by police in cities across the U.S. The footage unveils the hidden story of the violence many women have faced in abusive police encounters.Recorded encounters between women of color and police officers have been invaluable in bringing the reality of these interactions into the living rooms of otherwise unknowing Americans. The recordings are instrumental pieces of documentation and evidence, with the power to impact verdicts and galvanize the domestic struggle for human rights outside of the courtroom. They also are fraught with ethical issues that must be addressed by attorneys and activists hoping they effect change. Complexities such as implicit biases, editing and sourcing of videos, anonymity for those attacked and bystanders, and vicarious trauma on affected communities complicate use of violent police encounter videos."
      },
      {
        "id": "n290308SE",
        "doi": "10.1353/rap.2006.0024",
        "title": "&quot;Has Your Courage Rusted?&quot;: National Security and the Contested Rhetorical Norms of Republicanism in Post-Revolutionary America, 1798-1801",
        "authors": [
          {
            "id": "n3f8b5e73",
            "label": "Mercieca, Jennifer",
            "organizations": [
              {
                "id": "n11e515bb",
                "label": "Communication"
              }
            ]
          }
        ],
        "abstractText": null
      },
      {
        "id": "n92728SE",
        "doi": "10.1353/mfs.1998.0053",
        "title": "&quot;Unyoung, Unpoor, Unblack&quot;: John Updike and the Construction of Middle American Masculinity",
        "authors": [
          {
            "id": "n748e9dcd",
            "label": "Robinson, Sally",
            "organizations": [
              {
                "id": "nbc3b05cc",
                "label": "English"
              }
            ]
          }
        ],
        "abstractText": null
      },
      {
        "id": "n303405SE",
        "doi": null,
        "title": "''Smart'' temperature responsive surface-functionalized polyethylene films",
        "authors": [
          {
            "id": "nf01e95dd",
            "label": "Bergbreiter, David",
            "organizations": [
              {
                "id": "nce401957",
                "label": "Chemistry"
              }
            ]
          }
        ],
        "abstractText": null
      },
      {
        "id": "n384817SE",
        "doi": "10.1080/01426390903407152",
        "title": "'... Silver in the stars and gold in the morning sun': Non-farm rural landowners' motivations for rural living and attachment to their land",
        "authors": [
          {
            "id": "n0e3856dd",
            "label": "Brown, Robert",
            "organizations": [
              {
                "id": "n428d21e0",
                "label": "Landscape Architecture and Urban Planning"
              }
            ]
          }
        ],
        "abstractText": "Studies have identified that, given the opportunity, the majority of North Americans would prefer to live in small towns and rural areas. This preference is based in aesthetic notions linked to landscape features, personal meaning, and perceptions. In order to understand how the growing non-farm rural landowner population will influence the rural landscape, this research explored the motivations of non-farm rural landowners for living in rural areas, and their perceptions of their property. It involved five preliminary focus groups with farm and non farm landowners owning land in rural, urbanising rural, and urbanised rural areas, and four final focus groups. The research also included a survey of 944 landowners in Southern Ontario. People choose to live in rural areas because they are quiet, natural, open, private, and clean. In contrast, people chose to buy their properties for very practical reasons: location, cost, availability and quality of resources, and size. Results suggest that non-farm rural landowners prefer landscapes with trees and water, and landscape health, restorative benefits, and aesthetic quality are crucial. Associations with family, history, and activities provide the affective connection which supports ongoing efforts on their land. © 2010 Landscape Research Group Ltd."
      },
      {
        "id": "n330525SE",
        "doi": "10.3920/JCNS2011.x194",
        "title": "'Absorptive link': An absorptive capacity and alliance approach to biotechnological product success",
        "authors": [
          {
            "id": "nb8adcfe8",
            "label": "Ng, Desmond",
            "organizations": [
              {
                "id": "n6f91b86d",
                "label": "Agricultural Economics"
              }
            ]
          }
        ],
        "abstractText": "As biotechnologies are increasingly specialized and interrelated, the research question posed by this study is: what determines a biotechnology firm's product performance, if the development and commercialization of drug products require multiple technologies that are not possessed by any one firm? In drawing on concepts from strategic alliance and absorptive capacity research, this study develops and empirically examines a concept of 'absorptive link' in the biotechnology industry. A firm's absorptive link underscores that a firm's ability to develop new products stems from its prior knowledge in commercializing new applications of resources held by not only the firm, but also that of its alliance partners. Such a concept generated 6 testable hypotheses to which were mostly supported in this study's Poisson regression analysis of the biotechnology industry. This study concludes by offering three key contributions to product performance research."
      },
      {
        "id": "n341042SE",
        "doi": "10.1163/1937240X-00002335",
        "title": "'Anchialine' redefined as a subterranean estuary in a crevicular or cavernous geological setting",
        "authors": [
          {
            "id": "ned849b62",
            "label": "Iliffe, Thomas",
            "organizations": [
              {
                "id": "na4fc1057",
                "label": "Marine Biology"
              }
            ]
          }
        ],
        "abstractText": "© 2015 by The Crustacean Society. An improved understanding of the anchialine ecosystem and geology warrants a redefinition of the term 'anchialine. Originating from subareal biological observations, the term anchialine now encompasses chemical, physical, geological and biological elements within the subterranean realm. We propose a more accurate definition of the term anchialine as a tidally-influenced subterranean estuary located within crevicular and cavernous karst and volcanic terrains that extends inland to the limit of seawater penetration. This subterranean estuary is characterized by sharp physical and chemical stratification and merges with a marine system at the coast and a groundwater system inland. The anchialine ecosystem supports a relatively diverse biotic assemblage of stygobiotic species of marine origin dominated by members of Crustacea, both numerically and by species richness."
      },
      {
        "id": "n220696SE",
        "doi": null,
        "title": "'Benito Pérez Galdós’ ‘Author’s Prologue’ to The Grandfather, Novel in Five Acts (1897)",
        "authors": [
          {
            "id": "n648d7610",
            "label": "Miller, Stephen",
            "organizations": [
              {
                "id": "n6d109098",
                "label": "Hispanic Studies"
              }
            ]
          }
        ],
        "abstractText": null
      },
      {
        "id": "n336402SE",
        "doi": null,
        "title": "'Blue Angel' winter-hardy hibiscus (Hibiscus ×moscheutos L.)",
        "authors": [
          {
            "id": "n76e6ff4b",
            "label": "Pinchak, William",
            "organizations": [
              {
                "id": "n502c4153",
                "label": "Ecosystem Science and Management"
              }
            ]
          },
          {
            "id": "nbf063191",
            "label": "Malinowski, Dariusz",
            "organizations": [
              {
                "id": "nef483048",
                "label": "Soil and Crop Sciences"
              }
            ]
          }
        ],
        "abstractText": null
      }
    ]
  }
}

</details>

query:
```
{
  persons(
    query: "*",
    index: null,
    facets: [
      "positions.organizations.parent"
    ],
    params: {
      positions_organizations_parent_filter: [
        "College of Liberal Arts"
      ]
    },
    paging: {
      pageNumber: 0,
      pageSize: 5,
      sort: {
        orders: [{
          property: "name",
          direction: "DESC"
        }]
      }
    }
  ) {
    id,
    name,
    positions {
      id,
      label,
      type
      organizations {
        id,
        label,
        parent {
          id,
          label
        }
      }
    }
  }
}
```
<details> <summary>response:</summary>

{
  "data": {
    "persons": [
      {
        "id": "na3d48740",
        "name": "Zubairy, Sarah",
        "positions": [
          {
            "id": "nb9c16afa",
            "label": "Assistant Professor",
            "type": "FacultyPosition",
            "organizations": [
              {
                "id": "n5bb716ca",
                "label": "Economics",
                "parent": [
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n9e2c2753",
        "name": "Zhang, Yuzhe",
        "positions": [
          {
            "id": "ne0387d5f",
            "label": "Associate Professor",
            "type": "FacultyPosition",
            "organizations": [
              {
                "id": "n5bb716ca",
                "label": "Economics",
                "parent": [
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nde346a8a",
        "name": "Zapata, Gabriela",
        "positions": [
          {
            "id": "n6766118b",
            "label": "Director of Lower Division Spanish Instruction",
            "type": "FacultyPosition",
            "organizations": [
              {
                "id": "n6d109098",
                "label": "Hispanic Studies",
                "parent": [
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  },
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  }
                ]
              }
            ]
          },
          {
            "id": "n0b529b1f",
            "label": "Associate Professor",
            "type": "FacultyPosition",
            "organizations": [
              {
                "id": "n6d109098",
                "label": "Hispanic Studies",
                "parent": [
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  },
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "na24678ba",
        "name": "Yamauchi, Takashi",
        "positions": [
          {
            "id": "n20e0f9aa",
            "label": "Associate Professor",
            "type": "FacultyPosition",
            "organizations": [
              {
                "id": "n54556104",
                "label": "Psychological and Brain Sciences",
                "parent": [
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "na5354f30",
        "name": "Wright, Lori",
        "positions": [
          {
            "id": "n248000ec",
            "label": "Professor",
            "type": "FacultyPosition",
            "organizations": [
              {
                "id": "n0f7aa775",
                "label": "Anthropology",
                "parent": [
                  {
                    "id": "n70d8cedd",
                    "label": "College of Liberal Arts"
                  }
                ]
              }
            ]
          }
        ]
      }
    ]
  }
}

</details>

query:
```
{
  persons(type: "NonFacultyAcademic") {
    id,
    name,
    positions {
      id,
      label,
      type
      organizations {
        id,
        label,
        parent {
          id,
          label
        }
      }
    }
  }
}
```
<details> <summary>response:</summary>

{
  "data": {
    "persons": [
      {
        "id": "nad61fbd7",
        "name": "Pappa, Agathi Valentini",
        "positions": [
          {
            "id": "n7efc0a0c",
            "label": "Academic Program Coordinator",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nec4b9742",
                "label": "Energy Institute",
                "parent": null
              }
            ]
          },
          {
            "id": "nd34f642c",
            "label": "Program Coordinator I",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "ncd2c1725",
                "label": "Biological and Agricultural Engineering",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  },
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n99ebc338",
        "name": "Morpurgo, Benjamin",
        "positions": [
          {
            "id": "nc222a0e0",
            "label": "Executive Director",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n27760ea1",
                "label": "Institute for Genomic Medicine",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "n022f7f6c",
        "name": "Plotkin, Pamela",
        "positions": [
          {
            "id": "nfb71edb6",
            "label": "Director Texas Sea Grant",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nfeec0e89",
                "label": "College of Geosciences",
                "parent": [
                  {
                    "id": "nfeec0e89",
                    "label": "College of Geosciences"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          },
          {
            "id": "n8b9d58fe",
            "label": "Associate Research Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n472b8901",
                "label": "Oceanography",
                "parent": [
                  {
                    "id": "nfeec0e89",
                    "label": "College of Geosciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "ne8d603d7",
        "name": "Lopez, Roel",
        "positions": [
          {
            "id": "n416e212a",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n6aa6d975",
                "label": "Wildlife and Fisheries Sciences",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  },
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          },
          {
            "id": "n60d16728",
            "label": "Director of Natural Resources Institute",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n6aa6d975",
                "label": "Wildlife and Fisheries Sciences",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  },
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nf5b8d260",
        "name": "Girard, Audrey",
        "positions": [
          {
            "id": "n82f1fb0a",
            "label": "Associate Research Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nef483048",
                "label": "Soil and Crop Sciences",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n31ebd4a6",
        "name": "Mejia, Ethel",
        "positions": [
          {
            "id": "n38fe92c8",
            "label": "Data Analyst",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nb7f16f5c",
                "label": "University Libraries",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n4d12ac5b",
        "name": "Patil, Abhay",
        "positions": [
          {
            "id": "n2e121c88",
            "label": "Assistant Research Engineer - Faculty",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n1b7311c9",
                "label": "Mechanical Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n3641a7b1",
        "name": "Quigg, Antonietta",
        "positions": [
          {
            "id": "n7e89af0c",
            "label": "Associate Vice President for Research and Graduate Studies",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nc712f734",
                "label": "Texas A&M University at Galveston",
                "parent": [
                  {
                    "id": "nc712f734",
                    "label": "Texas A&M University at Galveston"
                  }
                ]
              }
            ]
          },
          {
            "id": "n65952e75",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "na4fc1057",
                "label": "Marine Biology",
                "parent": [
                  {
                    "id": "nc712f734",
                    "label": "Texas A&M University at Galveston"
                  }
                ]
              }
            ]
          },
          {
            "id": "n8211115b",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n472b8901",
                "label": "Oceanography",
                "parent": [
                  {
                    "id": "nfeec0e89",
                    "label": "College of Geosciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nf6164761",
        "name": "Wolfe, Clint",
        "positions": [
          {
            "id": "nd83624f3",
            "label": "Program Manager",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nc8b9f721",
                "label": "Texas A&M AgriLife Research",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n547d8b60",
        "name": "Bolton, Michael",
        "positions": [
          {
            "id": "nd31c52c1",
            "label": "Assistant Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nb7f16f5c",
                "label": "University Libraries",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nb33005d5",
        "name": "Elkins, Amber",
        "positions": [
          {
            "id": "n7f8a3230",
            "label": "Assistant Research Engineer - Faculty",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n158a9114",
                "label": "Industrial and Systems Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nf8b16ffc",
        "name": "Cornell, Karen",
        "positions": [
          {
            "id": "nd6bee321",
            "label": "Associate Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n1978f11a",
                "label": "College of Veterinary Medicine and Biomedical Sciences",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n512c144d",
        "name": "Harris, Danielle",
        "positions": [
          {
            "id": "n5cc32428",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n8a3da3c5",
                "label": "Recreation, Park and Tourism Science",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          },
          {
            "id": "n9dcc75d0",
            "label": "Assistant Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n68e48a80",
                "label": "College of Agriculture and Life Sciences",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n16527af5",
        "name": "Yoon, So",
        "positions": [
          {
            "id": "n735411f0",
            "label": "Associate Research Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n8627320c",
                "label": "College of Engineering",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n1ec94449",
        "name": "Pappas, Konstantinos",
        "positions": [
          {
            "id": "nacbd1ec7",
            "label": "Project Manager III",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nec4b9742",
                "label": "Energy Institute",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "n9dc51d17",
        "name": "Lowe, Virginia",
        "positions": [
          {
            "id": "n5b512cbc",
            "label": "Production Editor Iv",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nfeec0e89",
                "label": "College of Geosciences",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n53ca8674",
        "name": "Fullinwider, John",
        "positions": [
          {
            "id": "n2e4ef7f8",
            "label": "Associate Director",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n043bc62c",
                "label": "Health Science Center",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n8a168326",
        "name": "Shlomo, Shalom",
        "positions": null
      },
      {
        "id": "n48c355ae",
        "name": "Konganti, Kranti",
        "positions": [
          {
            "id": "ne48839fd",
            "label": "Associate Director",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n2195ed96",
                "label": "Institute of Genome Sciences and Society",
                "parent": null
              }
            ]
          },
          {
            "id": "n2e079af7",
            "label": "Senior Systems Analyst II",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n3af9f727",
                "label": "Veterinary Pathobiology",
                "parent": [
                  {
                    "id": "n1978f11a",
                    "label": "College of Veterinary Medicine and Biomedical Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n4b788e45",
        "name": "Bufford, Mandrell",
        "positions": null
      },
      {
        "id": "naf1cf1a2",
        "name": "Krishnadevarajan, Pradip",
        "positions": [
          {
            "id": "n4be651f2",
            "label": "Associate Research Engineer - Faculty",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n2c8a5f2c",
                "label": "Engineering Technology and Industrial Distribution",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n0cb1b858",
        "name": "Fahrenwald, Nancy",
        "positions": [
          {
            "id": "n03c03f38",
            "label": "Faculty Fellow",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "naaf1953a",
                "label": "Center for Health Systems and Design",
                "parent": null
              }
            ]
          },
          {
            "id": "n6c954b43",
            "label": "Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n699ce527",
                "label": "College of Nursing",
                "parent": [
                  {
                    "id": "n043bc62c",
                    "label": "Health Science Center"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n0f782eb9",
        "name": "Rosenheim, Nathanael",
        "positions": [
          {
            "id": "nab20a35c",
            "label": "Associate Research Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n428d21e0",
                "label": "Landscape Architecture and Urban Planning",
                "parent": [
                  {
                    "id": "n4b0538ce",
                    "label": "College of Architecture"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n6a4094cd",
        "name": "Murano, Elsa",
        "positions": [
          {
            "id": "n4cad4f91",
            "label": "Director Institute",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nea533578",
                "label": "Nutrition and Food Science",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nb62b9d15",
        "name": "Reddy, Indra",
        "positions": [
          {
            "id": "n5a178ea2",
            "label": "Faculty Fellow",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "naaf1953a",
                "label": "Center for Health Systems and Design",
                "parent": null
              }
            ]
          },
          {
            "id": "n3cc1b75d",
            "label": "Professor and Founding Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n1f1795b1",
                "label": "Irma Lerma Rangel College of Pharmacy",
                "parent": [
                  {
                    "id": "n043bc62c",
                    "label": "Health Science Center"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nf25665e6",
        "name": "Reynolds, Larry",
        "positions": [
          {
            "id": "n18982c0d",
            "label": "Library Associate I",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nb7f16f5c",
                "label": "University Libraries",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n1cafa7b6",
        "name": "Becker, Cecily",
        "positions": [
          {
            "id": "na6702578",
            "label": "Director of Externship Program and Senior Lecturer",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n52ff53af",
                "label": "School of Law",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n1aca08e4",
        "name": "Golovko, Andrei",
        "positions": [
          {
            "id": "n62cb7752",
            "label": "Production Manager",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n27760ea1",
                "label": "Institute for Genomic Medicine",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "n3b70003c",
        "name": "Wagner, Alfred",
        "positions": [
          {
            "id": "n1b0ab052",
            "label": "Extension Associate",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nd95812f2",
                "label": "Horticultural Science",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n3631143f",
        "name": "Watzak, Bree",
        "positions": [
          {
            "id": "na466641e",
            "label": "Clinical Assistant Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nb2b79210",
                "label": "Pharmacy Practice",
                "parent": [
                  {
                    "id": "n1f1795b1",
                    "label": "Irma Lerma Rangel College of Pharmacy"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n33f756bb",
        "name": "Childress, Laurel",
        "positions": [
          {
            "id": "n7364a4c7",
            "label": "Expedition Project Manager/Staff Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n69245a4e",
                "label": "International Ocean Discovery Program",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "n3e0f7747",
        "name": "Hahn, Douglas",
        "positions": [
          {
            "id": "n36323000",
            "label": "IT Manager",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nb7f16f5c",
                "label": "University Libraries",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "naf9f7163",
        "name": "Perez, Lisa",
        "positions": [
          {
            "id": "nae83ddc5",
            "label": "Research Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nce401957",
                "label": "Chemistry",
                "parent": [
                  {
                    "id": "n7267255d",
                    "label": "College of Science"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n6b60a83e",
        "name": "Barteau, Mark",
        "positions": [
          {
            "id": "n5a2bc8b1",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nf2f6fdfa",
                "label": "Chemical Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          },
          {
            "id": "n85b3c2c8",
            "label": "Vice President for Research",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n76004c6f",
                "label": "Office of the Provost and Executive Vice President",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n3e2d13bc",
        "name": "Schneider, Dean",
        "positions": [
          {
            "id": "n6196648c",
            "label": "Fellow",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n952c84ff",
                "label": "Texas A&M Engineering Experiment Station (TEES)",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          },
          {
            "id": "n0677e20c",
            "label": "Director of Operations, Southern Regional Manufacturing Center",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nec4b9742",
                "label": "Energy Institute",
                "parent": null
              }
            ]
          },
          {
            "id": "n3a5ee238",
            "label": "Adjunct Professor of Practice",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n158a9114",
                "label": "Industrial and Systems Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n1c0afde6",
        "name": "Meeks, Meghyn",
        "positions": [
          {
            "id": "n3a87ca79",
            "label": "Research Associate",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nc8b9f721",
                "label": "Texas A&M AgriLife Research",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n79cc9ac6",
        "name": "Sharma, Anupma",
        "positions": [
          {
            "id": "n6eaf34b0",
            "label": "Postdoctoral Research Associate",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nc8b9f721",
                "label": "Texas A&M AgriLife Research",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n338fd8c9",
        "name": "Brumbelow, Kelly",
        "positions": [
          {
            "id": "n93be54a0",
            "label": "Associate Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n4f6c1991",
                "label": "Civil Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n10715a48",
        "name": "Fowler, Debra",
        "positions": [
          {
            "id": "n4f5c1b07",
            "label": "Director",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "ncf3c6d7a",
                "label": "Center for Teaching Excellence",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "n6b712c5d",
        "name": "Powers, Timothy",
        "positions": [
          {
            "id": "n9e54764a",
            "label": "Director, Aggie Honor System Office",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n769e07e6",
                "label": "Division of Student Affairs",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "nc20d07a9",
        "name": "Fairchild, Amy@en-US",
        "positions": [
          {
            "id": "n126e0c69",
            "label": "Professor and Associate Dean of Academic Affairs",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n224b5f5a",
                "label": "Health Policy and Management",
                "parent": [
                  {
                    "id": "n7fa1fd47",
                    "label": "School of Public Health"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n573c6961",
        "name": "Woodard, Susan",
        "positions": [
          {
            "id": "n2736731d",
            "label": "Research Scientist - Faculty",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "ncfe61f1d",
                "label": "National Center for Therapeutics Manufacturing",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "nf0375f36",
        "name": "Gill, Clare",
        "positions": [
          {
            "id": "nb3306cc7",
            "label": "Executive Associate Dean and Associate Dean for Research",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n68e48a80",
                "label": "College of Agriculture and Life Sciences",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          },
          {
            "id": "n1c87e556",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n68895a59",
                "label": "Animal Science",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "ndd7ffe32",
        "name": "Johnson, Valen",
        "positions": [
          {
            "id": "n0c29c478",
            "label": "Professor and Head",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nd72a586b",
                "label": "Statistics",
                "parent": [
                  {
                    "id": "n7267255d",
                    "label": "College of Science"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n1a07da90",
        "name": "Parrish, Blake",
        "positions": [
          {
            "id": "n9ab208ec",
            "label": "Director",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n568e1695",
                "label": "Mays Business School",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n99ab4e51",
        "name": "Schramm, Michael",
        "positions": [
          {
            "id": "nca835f58",
            "label": "Research Associate",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nc0f1a03e",
                "label": "Texas Water Resources Institute",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "n23071727",
        "name": "Bageshwar, Umesh",
        "positions": [
          {
            "id": "n0ad02dcf",
            "label": "Research Assistant Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "na7893461",
                "label": "Molecular and Cellular Medicine",
                "parent": [
                  {
                    "id": "nd14d726a",
                    "label": "College of Medicine"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n66679ebc",
        "name": "Peterson, Steven",
        "positions": [
          {
            "id": "n1e20785c",
            "label": "Associate Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nb2b79210",
                "label": "Pharmacy Practice",
                "parent": [
                  {
                    "id": "n1f1795b1",
                    "label": "Irma Lerma Rangel College of Pharmacy"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nd0a0a020",
        "name": "Kotinek, Jonathan",
        "positions": [
          {
            "id": "n98c2f75f",
            "label": "Associate Program Director, LAUNCH: Honors",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n769e07e6",
                "label": "Division of Student Affairs",
                "parent": null
              }
            ]
          }
        ]
      },
      {
        "id": "nd2caf464",
        "name": "Poston, John",
        "positions": [
          {
            "id": "nec50846b",
            "label": "Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n5ad795f8",
                "label": "Nuclear Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n921286d0",
        "name": "Runge, Edward",
        "positions": [
          {
            "id": "n49aed151",
            "label": "Program Director",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "nef483048",
                "label": "Soil and Crop Sciences",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "ne5797eff",
        "name": "McCallum, Kathryn",
        "positions": [
          {
            "id": "ne4068c21",
            "label": "Associate Dean",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n699ce527",
                "label": "College of Nursing",
                "parent": [
                  {
                    "id": "n043bc62c",
                    "label": "Health Science Center"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nbe7cfcc7",
        "name": "Nounou, Hazem",
        "positions": [
          {
            "id": "n2301ab6f",
            "label": "Itochu Professor",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n5efbf3f4",
                "label": "Electrical and Computer Engineering (Qatar)",
                "parent": [
                  {
                    "id": "ned9805ff",
                    "label": "Texas A&M University at Qatar"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nef9a3e7b",
        "name": "Taylor, Eric",
        "positions": [
          {
            "id": "n5c761b9c",
            "label": "Silviculturist Iii",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n502c4153",
                "label": "Ecosystem Science and Management",
                "parent": [
                  {
                    "id": "n68e48a80",
                    "label": "College of Agriculture and Life Sciences"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "nb98ca959",
        "name": "Kannaiyan, Kumaran",
        "positions": [
          {
            "id": "n46f868ee",
            "label": "Assistant Research Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n69d9e085",
                "label": "Mechanical Engineering (Qatar)",
                "parent": [
                  {
                    "id": "ned9805ff",
                    "label": "Texas A&M University at Qatar"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n66ebdb01",
        "name": "Hannibal, Bryce",
        "positions": [
          {
            "id": "nbfa7aa93",
            "label": "Assistant Research Scientist",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n3c6ba885",
                "label": "Bush School of Government and Public Service",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n35cc3567",
        "name": "Kurwitz, Richard",
        "positions": [
          {
            "id": "n399f88b4",
            "label": "Associate Research Engineer - Faculty",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n5ad795f8",
                "label": "Nuclear Engineering",
                "parent": [
                  {
                    "id": "n8627320c",
                    "label": "College of Engineering"
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        "id": "n3e74a78c",
        "name": "Probasco, Robert",
        "positions": [
          {
            "id": "naab9f1b0",
            "label": "Director, Low Income Tax Clinic and Senior Lecturer",
            "type": "NonFacultyAcademicPosition",
            "organizations": [
              {
                "id": "n52ff53af",
                "label": "School of Law",
                "parent": [
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Corpus Christi"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Texarkana"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University Commerce"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Galveston"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University at Qatar"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University - College Station, Texas, United States"
                  },
                  {
                    "id": "n69864197",
                    "label": "Texas A&M University"
                  }
                ]
              }
            ]
          }
        ]
      }
    ]
  }
}

