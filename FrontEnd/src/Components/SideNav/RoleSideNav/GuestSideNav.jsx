import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Guest
 */
export default class GuestSideNav extends Component {
    render() {
        return (
            <SideNavFramework links={[
                halamanUtama
            ]}/>
        );
    }
}
