import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

export default class QALeadSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        {
                            link: "#",
                            title: "Halaman Utama",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Hasil Pemeriksaan",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
