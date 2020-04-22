import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama, hasilPemeriksaan } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk QA Lead
 */
export default class QALeadSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                {...this.props}
                links={
                    [
                        halamanUtama,
                        hasilPemeriksaan
                    ]
                }
            />
        );
    }
}
