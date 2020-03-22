import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

export default class SuperuserSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        {
                            link: "#",
                            title: "Dashboard Employee",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Dashboard Kantor Cabang",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Pengguna",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Kantor Cabang",
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
                            title: "Bukti Rekomendasi",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
