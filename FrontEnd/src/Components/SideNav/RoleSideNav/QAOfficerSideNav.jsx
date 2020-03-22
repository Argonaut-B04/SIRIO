import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

export default class QAOfficerSideNav extends Component {
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
                        },
                        {
                            link: "#",
                            title: "Tugas Pemeriksaan",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Rekomendasi",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Dashboard",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
