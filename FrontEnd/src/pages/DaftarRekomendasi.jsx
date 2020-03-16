import React from "react";
import TableRekomendasi from "../Components/Tables/Rekomendasi/TabelRekomendasi";
import SideNav from "../Components/SideNav/SideNav";
import classes from "./DaftarRekomendasi.module.css";

const DaftarRekomendasi = () => {
    return (
        <>
            <SideNav
                username="Rudanto"
                role="QA Officer Operational Risk"
                login={true}
                logHyperlink="#"
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
                            active: true
                        },
                        {
                            link: "#",
                            title: "Dashboard",
                            active: false
                        }
                    ]
                }
            />
            <div className={classes.mainContent}>
                <div className={classes.pageContent}>
                    <TableRekomendasi />
                </div>
            </div>
        </>
    );
};

export default DaftarRekomendasi;