import { environment } from '../environments/environment';

export const processSolrDocument = (sections: any, displayView: any, callback: Function) => {
    for (let i = 0; i < sections.length; i++) {
        let section = sections[i];

        for (let j = 0; j < displayView.tabs.length; j++) {
            let tab = displayView.tabs[j];

            for (let k = 0; k < tab.sections.length; k++) {
                let tabSection = tab.sections[k];

                if (tabSection.name === section) {
                    callback(section, tabSection);
                    break;
                }
            }
        }
    }
};
